package com.kal.beum.myinfo.data.network

import com.kal.beum.core.data.ApiConstants
import com.kal.beum.core.data.AppUserCache
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.login.data.dto.LoginResponseDto
import com.kal.beum.login.domain.SocialToken
import com.kal.beum.main.domain.SocialType
import com.kal.beum.main.domain.UserInfo
import com.kal.beum.main.domain.UserRequestDto
import com.kal.beum.myinfo.data.dto.MyBoardInfoDto
import com.kal.beum.myinfo.data.dto.MyContentDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class KtorMyInfoDataSource(private val httpClient: HttpClient) : RemoteMyInfoDataSource {
    override suspend fun getMyContents(userId: Int): Result<MyBoardInfoDto, DataError.Remote> {
        println("getMyContents start : $userId")
        val result = httpClient.get(ApiConstants.Endpoints.MY_BOARD_INFO) {
            headers {
                AppUserCache.userInfo?.accessToken?.let {
                    append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                }
            }
            url {
                parameters.append(ApiConstants.KEY.KEY_PAGE, 0.toString())
                parameters.append(ApiConstants.KEY.KEY_SIZE, 10.toString())
                parameters.append(ApiConstants.KEY.KEY_IS_DEVIL, AppUserCache.isDevil.toString())
            }
        }
        println("getMyContents!!!!! : $result")
        return if (result.status.value == 200) {
            Result.Success(result.body<MyBoardInfoDto>())
        } else {
            Result.Error(DataError.Remote.REQUEST_ERROR)
        }
    }

    override suspend fun getMyReply(userId: Int): Result<MyBoardInfoDto, DataError.Remote> {
        println("getMyReply start : $userId")
        val result = httpClient.get(ApiConstants.Endpoints.MY_COMMENT_LIST) {
            headers {
                AppUserCache.userInfo?.accessToken?.let {
                    append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                }
            }
//            setBody(UserRequestDto(userId.toString(), AppUserCache.userInfo?.nickName ?: ""))
            url {
                parameters.append(ApiConstants.KEY.KEY_PAGE, 0.toString())
                parameters.append(ApiConstants.KEY.KEY_SIZE, 10.toString())
                parameters.append(ApiConstants.KEY.KEY_IS_DEVIL, AppUserCache.isDevil.toString())
            }
        }
//        println("getMyContents : $result")
//        return if (result.status.value == 200) {
//            Result.Success(result.body<MyBoardInfoDto>())
//        } else {
//            Result.Error(DataError.Remote.REQUEST_ERROR)
//        }
        return Result.Error(DataError.Remote.REQUEST_ERROR)
    }

    override suspend fun reportUser(myContentId: Int): Result<Unit, DataError.Remote> {
        return Result.Success(Unit)
    }

    override suspend fun refreshLoginInfo(
        socialType: Int, socialToken: SocialToken
    ): Result<UserInfo, DataError.Remote> {

        val response = httpClient.post(ApiConstants.Endpoints.SIGNIN) {
            setBody(
                mapOf(
                    "accessToken" to socialToken.accessToken,
                    "socialType" to SocialType.toName(socialType)
                )
            )
        }
        if (response.status.value == 200) {
            val responseBody = response.body<LoginResponseDto>()
            val userInfo = UserInfo(
                userId = responseBody.userId,
                nickName = responseBody.nickName,
                socialType = socialType,
                email = responseBody.email,
                sessionKey = "",
                accessToken = responseBody.tokenSet?.accessToken ?: socialToken.accessToken,
                refreshToken = responseBody.tokenSet?.refreshToken ?: socialToken.refreshToken,
                profileImageId = responseBody.profileImageId,
                needSignUp = responseBody.needSignUp
            )
            return Result.Success(userInfo)
        } else {
            return Result.Error(DataError.Remote.LOGIN_FAILED)
        }
    }
}