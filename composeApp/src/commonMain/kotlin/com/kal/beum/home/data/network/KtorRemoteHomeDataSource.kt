package com.kal.beum.home.data.network

import com.kal.beum.core.data.ApiConstants
import com.kal.beum.core.data.AppUserCache
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.home.data.dto.HomeCommentDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText

class KtorRemoteHomeDataSource(
    private val httpClient: HttpClient
) : RemoteHomeDataSource {
    override suspend fun getHomeCommentList(isDevil: Boolean): Result<List<HomeCommentDto>, DataError.Remote> {
        println("getHomeCommentList")
        val result = httpClient.get(ApiConstants.Endpoints.TOP_100_COMMENTS) {
            headers {
                AppUserCache.userInfo?.accessToken?.let {
                    append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                }
            }
            url {
                parameters.append(ApiConstants.KEY.KEY_IS_DEVIL, (isDevil).toString())
            }
        }
        println("result!@!@!@!@!@! : ${result.bodyAsText()}")
        return Result.Success(result.body())
    }

}