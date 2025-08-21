package com.kal.beum.main.data.network

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.login.domain.LoginClient
import com.kal.beum.login.domain.SocialToken
import com.kal.beum.main.data.database.UserInfoEntity
import com.kal.beum.main.data.mappers.toUserInfo
import com.kal.beum.main.data.mappers.toUserInfoEntity
import com.kal.beum.main.domain.UserInfo
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class SdkLoginDataSource(
    private val loginClients: Map<Int, LoginClient>, private val httpClient: HttpClient
) : RemoteLoginDataSource {

    override suspend fun login(socialType: Int): Result<UserInfo, DataError.Remote> {
        println("login : $socialType")
        return try {
            val socialToken = getSocialToken(socialType)
            val userInfo = fetchUserInfoFromServer(socialToken, socialType)
            Result.Success(userInfo)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }

    override suspend fun logout(userInfo: UserInfo): Result<Unit, DataError.Remote> {
        println("logout : ")
        val client =
            loginClients[userInfo.socialType] ?: return Result.Error(DataError.Remote.UNKNOWN)
        client.logout()
        return Result.Success(Unit)

    }

    private suspend fun getSocialToken(socialType: Int): SocialToken {
        val client = loginClients[socialType] ?: throw Exception("클라이언트 없음")
        println("getSocialToken :   $socialType")
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
        socialToken: SocialToken, socialType: Int
    ): UserInfo {
//        val response = httpClient.post("your-api-endpoint") {
//            setBody(mapOf(
//                "socialToken" to socialToken,
//                "socialType" to socialType
//            ))
//        }


        println("fetchUserInfoFromServer : $socialToken")
        return UserInfo("1", "고라니", socialType, "jhlee@gmail.com", "1111")
    }
}

