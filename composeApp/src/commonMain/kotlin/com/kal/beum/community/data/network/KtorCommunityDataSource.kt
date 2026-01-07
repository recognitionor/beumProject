package com.kal.beum.community.data.network

import com.kal.beum.community.data.dto.CategoryDto
import com.kal.beum.community.data.dto.CategoryMapDto
import com.kal.beum.community.data.dto.CommunityDto
import com.kal.beum.core.data.ApiConstants
import com.kal.beum.core.data.ApiConstants.KEY.KEY_BOARD_ID
import com.kal.beum.core.data.ApiConstants.KEY.KEY_TARGET_USER_ID
import com.kal.beum.core.data.AppUserCache
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import io.ktor.client.HttpClient
import com.kal.beum.core.data.safeCall
import com.kal.beum.core.domain.Result.*
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText

class KtorCommunityDataSource(private val httpClient: HttpClient) : RemoteCommunityDataSource {
    override suspend fun getCategoryList(): Result<CategoryMapDto, DataError.Remote> {
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

    override suspend fun getCommunity(
        page: Int, size: Int, categoryId: Int, isDevil: Boolean
    ): Result<CommunityDto, DataError.Remote> {
        println("KtorCommunityDataSource getCommunityList : $categoryId")
        val result = safeCall<CommunityDto> {
            httpClient.get(ApiConstants.Endpoints.BOARDS) {
                headers {
                    AppUserCache.userInfo?.accessToken?.let {
                        append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                    }
                }
                url {
                    parameters.append(ApiConstants.KEY.KEY_PAGE, page.toString())
                    parameters.append(ApiConstants.KEY.KEY_SIZE, size.toString())
                    parameters.append(ApiConstants.KEY.KEY_IS_DEVIL, isDevil.toString())
                    if (categoryId > 0) {
                        parameters.append(ApiConstants.KEY.KEY_CATEGORY_ID, categoryId.toString())
                    }

                }
            }
        }

        return result
    }

    override suspend fun pickComment(
        targetUserId: String, boardId: String
    ): Result<Boolean, DataError.Remote> {
        println("ktor : pickComment")
        val result = safeCall<String> {
            httpClient.post(ApiConstants.Endpoints.POINT) {
                headers {
                    AppUserCache.userInfo?.accessToken?.let {
                        append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                    }
                }
                url {
                    parameters.append(KEY_BOARD_ID, targetUserId)
                    parameters.append(KEY_TARGET_USER_ID, boardId)
                }
            }
        }

        println("~~~~~~~~~~~~ : $result")

        return when (result) {
            is Success -> Success(true)
            is Error -> Error(result.error)
            is Progress -> Progress()
        }
    }
}
