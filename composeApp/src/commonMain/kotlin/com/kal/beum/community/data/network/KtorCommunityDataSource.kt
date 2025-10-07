package com.kal.beum.community.data.network

import com.kal.beum.community.data.dto.CategoryDto
import com.kal.beum.community.data.dto.CommunityDto
import com.kal.beum.core.data.ApiConstants
import com.kal.beum.core.data.AppUserCache
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers

class KtorCommunityDataSource(private val httpClient: HttpClient) : RemoteCommunityDataSource {
    override suspend fun getCategoryList(): Result<List<CategoryDto>, DataError.Remote> {
        val response = httpClient.get(ApiConstants.Endpoints.CATEGORY_LIST) {
            headers {
                AppUserCache.accessToken?.let {
                    append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                }
            }
        }
        return Result.Success(response.body())
    }

    override suspend fun getCommunity(
        page: Int,
        size: Int,
        categoryId: Int,
        isDevil: Boolean
    ): Result<CommunityDto, DataError.Remote> {
        println("KtorCommunityDataSource getCommunityList : $categoryId")
        val response = httpClient.get(ApiConstants.Endpoints.BOARDS) {
            headers {
                AppUserCache.accessToken?.let {
                    append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                }
            }
            url {
                parameters.append(ApiConstants.KEY.KEY_PAGE, page.toString())
                parameters.append(ApiConstants.KEY.KEY_SIZE, size.toString())
                parameters.append(ApiConstants.KEY.KEY_IS_DEVIL, isDevil.toString())
                parameters.append(ApiConstants.KEY.KEY_CATEGORY_ID, categoryId.toString())
            }
        }
        return if (response.status.value == 200 && response.body<CommunityDto>().boardList.isNotEmpty()) {
            Result.Success(response.body())
        } else {
            Result.Error(DataError.Remote.REQUEST_ERROR)
        }
    }
}
