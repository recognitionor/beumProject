package com.kal.beum.main.data.network

import com.kal.beum.core.data.ApiConstants
import com.kal.beum.core.data.AppUserCache
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.login.data.dto.LoginResponseDto
import com.kal.beum.login.data.dto.TokenSetDto
import com.kal.beum.login.domain.LoginClient
import com.kal.beum.login.domain.SocialToken
import com.kal.beum.main.domain.SocialType
import com.kal.beum.main.domain.UserInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Parameters
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class SdkLoginDataSource(
    private val loginClients: Map<Int, LoginClient>, private val httpClient: HttpClient
) : RemoteLoginDataSource {

    override suspend fun signup(
        socialType: Int, accessToken: String, refreshToken: String
    ): Result<UserInfo, DataError.Remote> {
        println("signup : $socialType")
        return try {
            val userInfo =
                fetchUserInfoFromServer(SocialToken(accessToken, refreshToken), socialType, true)
            println("signup userInfo : $userInfo")
            Result.Success(userInfo)
        } catch (e: CancellationException) {
            println(e.message)
            throw e
        } catch (e: Exception) {
            println(e.message)
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }

    override suspend fun login(socialType: Int): Result<UserInfo, DataError.Remote> {
        println("login : $socialType")
        return try {
            val socialToken = getSocialToken(socialType)
            val userInfo = fetchUserInfoFromServer(socialToken, socialType)
            Result.Success(userInfo)
        } catch (e: CancellationException) {
            println("e.message1")
            println(e.message)
            throw e
        } catch (e: Exception) {
            println("e.message2")
            println(e.message)
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }

    override suspend fun logout(userInfo: UserInfo): Result<Unit, DataError.Remote> {
        val client =
            loginClients[userInfo.socialType] ?: return Result.Error(DataError.Remote.UNKNOWN)
        client.logout()
        return Result.Success(Unit)
    }

    override suspend fun withdraw(): Result<Unit, DataError.Remote> {
        val result = httpClient.post(ApiConstants.Endpoints.WITHDRAW) {
            headers {
                AppUserCache.userInfo?.accessToken?.let {
                    append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                }
            }
        }
        println("withdraw result : $result")
        return Result.Success(Unit)
    }

    override suspend fun updateFcmToken(
        userInfo: UserInfo, token: String
    ) {
        val response = httpClient.post(ApiConstants.Endpoints.FIREBASE_TOKEN) {
            headers {
                AppUserCache.userInfo?.accessToken?.let {
                    append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                }
            }
            setBody(
                mapOf(
                    ApiConstants.KEY.KEY_TOKEN to token,
                )
            )
        }
        println("updateFcmToken response $response")
    }

    override suspend fun refreshAccessToken(result: UserInfo): Result<UserInfo?, DataError.Remote> {
        val response = httpClient.post(ApiConstants.Endpoints.REFRESH_ACCESS_TOKEN) {
            headers {
                append(ApiConstants.KEY.KEY_AUTH_TOKEN, result.accessToken)
            }
            setBody(FormDataContent(Parameters.build {
                append(
                    ApiConstants.KEY.KEY_REFRESH_TOKEN, result.refreshToken
                )
            }))
        }
        if (response.status.value == 200) {
            try {
                val refreshTokenSet = response.body<TokenSetDto>()
                val tempResult = result.copy(
                    refreshToken = refreshTokenSet.refreshToken,
                    accessToken = refreshTokenSet.accessToken
                )
                // 만약에 리프레시된 토큰이 존재하면 토큰값 변경 해서 return
                return Result.Success(tempResult)
            } catch (_: Exception) {
            }
            // 리프레시 된 토큰이 없는경우 그대로 사용
            return Result.Success(result)
        } else {
            // 리프레시 시도시 에러가 나는 경우는 Null 을 반환 하여 새로이 로그인 하도록 유도.
            return Result.Success(null)
        }
    }

    private suspend fun getSocialToken(socialType: Int): SocialToken {
        val client = loginClients[socialType] ?: throw Exception("클라이언트 없음")
        return suspendCoroutine { cont ->
            client.login(socialType) { token, error ->
                if (error == null && token != null) {
                    cont.resume(token)
                } else {
                    cont.resumeWithException(Exception("소셜 로그인 실패: $error"))
                }
            }
        }
    }

    private suspend fun fetchUserInfoFromServer(
        socialToken: SocialToken, socialType: Int, needSignup: Boolean = false
    ): UserInfo {
        val path = if (needSignup) {
            ApiConstants.Endpoints.SIGNUP
        } else {
            ApiConstants.Endpoints.SIGNIN
        }

        println("fetchUserInfoFromServer")

        val response = httpClient.post(path) {
            setBody(
                mapOf(
                    "accessToken" to socialToken.accessToken,
                    "socialType" to SocialType.toName(socialType)
                )
            )
        }
        println("fetchUserInfoFromServer : $response")
        when (response.status.value) {
            200 -> {
                println("response 응답: ${response.bodyAsText()}")
                val responseBody = response.body<LoginResponseDto>()
                println("성공 응답: $responseBody")
                return UserInfo(
                    userId = responseBody.userId,
                    nickName = responseBody.nickName,
                    socialType = socialType,
                    email = responseBody.email,
                    sessionKey = "",
                    accessToken = responseBody.tokenSet?.accessToken ?: socialToken.accessToken,
                    refreshToken = responseBody.tokenSet?.refreshToken ?: socialToken.refreshToken,
                    profileImageId = responseBody.profileImageId,
                    needSignUp = responseBody.needSignUp,
                    angelPoint = responseBody.angelPoint,
                    devilPoint = responseBody.devilPoint
                )
            }

            400 -> {
                val errorBody = response.bodyAsText()
                println("잘못된 요청: $errorBody")
                throw Exception("잘못된 요청 데이터")
            }

            401 -> {
                val errorBody = response.bodyAsText()
                println("인증 실패: $errorBody")
                throw Exception("소셜 토큰이 유효하지 않음")
            }

            500 -> {
                val errorBody = response.bodyAsText()
                println("서버 오류: $errorBody")
                throw Exception("서버 내부 오류")
            }

            else -> {
                val errorBody = response.bodyAsText()
                println("알 수 없는 오류 (${response.status}): $errorBody")
                throw Exception("서버 오류: ${response.status}")
            }
        }
    }
}

