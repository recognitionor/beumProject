package com.kal.beum.write.data.network

import com.kal.beum.community.data.dto.CategoryMapDto
import com.kal.beum.core.data.ApiConstants
import com.kal.beum.core.data.AppUserCache
import com.kal.beum.core.data.safeCall
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers

class KtorWriteCategoryDataSource(
    private val httpClient: HttpClient
) : RemoteWriteCategoryDataSource {
    override suspend fun getWriteCategoryList(): Result<CategoryMapDto, DataError.Remote> {
        return safeCall {
            httpClient.get(ApiConstants.Endpoints.CATEGORY_LIST) {
                headers {
                    AppUserCache.userInfo?.accessToken?.let {
                        append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                    }
                }
            }
        }
    }
}