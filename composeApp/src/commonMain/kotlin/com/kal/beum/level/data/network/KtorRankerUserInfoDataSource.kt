package com.kal.beum.level.data.network

import com.kal.beum.core.data.ApiConstants
import com.kal.beum.core.data.AppUserCache
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.level.data.dto.RankerUserInfoDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers

class KtorRankerUserInfoDataSource(private val httpClient: HttpClient) :
    RemoteRankerUserInfoDataSource {
    override suspend fun getRankerList(isDevel: Boolean): Result<List<RankerUserInfoDto>, DataError.Remote> {
        val result = httpClient.get(ApiConstants.Endpoints.USER_SORTED) {
            headers {
                AppUserCache.userInfo?.accessToken?.let {
                    append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                }
            }
            url {
                parameters.append(ApiConstants.KEY.KEY_IS_DEVIL, isDevel.toString())
            }
        }

        println("getRankerList : $result")

        return Result.Error(DataError.Remote.REQUEST_ERROR)

    }
}