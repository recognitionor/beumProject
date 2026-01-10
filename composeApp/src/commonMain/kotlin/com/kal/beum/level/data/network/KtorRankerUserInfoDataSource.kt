package com.kal.beum.level.data.network

import com.kal.beum.core.data.ApiConstants
import com.kal.beum.core.data.AppUserCache
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.level.data.dto.RankerUserInfoDto
import com.kal.beum.core.data.safeCall
import com.kal.beum.level.data.dto.RankerListResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText

class KtorRankerUserInfoDataSource(private val httpClient: HttpClient) :
    RemoteRankerUserInfoDataSource {
    override suspend fun getRankerList(isDevel: Boolean): Result<List<RankerUserInfoDto>, DataError.Remote> {
        val result = safeCall<RankerListResponseDto> {
            httpClient.get(ApiConstants.Endpoints.USER_SORTED) {
                headers {
                    AppUserCache.userInfo?.accessToken?.let {
                        append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                    }
                }
                url {
                    parameters.append(ApiConstants.KEY.KEY_IS_DEVIL, isDevel.toString())
                }
            }
        }

        return when (result) {
            is Result.Success -> {
                val rankerList = result.data.sortedPointUserDtoList.mapIndexed { index, item ->
                    RankerUserInfoDto(
                        userId = item.userId.toString(),
                        nickname = item.nickname,
                        profileImageUrl = "",
                        level = 0,
                        rank = index + 1,
                        score = item.point,
                        isDevil = isDevel
                    )
                }
                Result.Success(rankerList)
            }

            is Result.Error -> Result.Error(result.error)
            is Result.Progress -> Result.Progress()
        }
    }
}