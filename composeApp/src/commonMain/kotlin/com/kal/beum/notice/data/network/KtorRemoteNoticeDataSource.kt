package com.kal.beum.notice.data.network

import com.kal.beum.core.data.ApiConstants
import com.kal.beum.core.data.AppUserCache
import com.kal.beum.core.data.safeCall
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.core.domain.Result.Error
import com.kal.beum.core.domain.Result.Progress
import com.kal.beum.core.domain.Result.Success
import com.kal.beum.notice.data.dto.NoticeMapDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post

class KtorRemoteNoticeDataSource(
    private val httpClient: HttpClient
) : RemoteNoticeDataSource {
    override suspend fun getNoticeMap(): Result<NoticeMapDto, DataError.Remote> {
        return safeCall {
            httpClient.get(ApiConstants.Endpoints.NOTICE) {
                headers {
                    AppUserCache.userInfo?.accessToken?.let {
                        append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                    }
                }
            }

        }
    }

    override suspend fun toggleAlarm(enable: Boolean): Result<Boolean, DataError.Remote> {
        val result = safeCall<String> {
            httpClient.post(ApiConstants.Endpoints.ALARM) { // Make sure ALARM endpoint is defined in ApiConstants
                headers {
                    AppUserCache.userInfo?.accessToken?.let {
                        append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                    }
                }
                url {
                    parameters.append("enableAlarm", enable.toString())
                }
            }
        }

        return when (result) {
            is Success -> Success(true)
            is Error -> Error(result.error)
            is Progress -> Progress()
        }
    }
}