package com.kal.beum.notice.data.network

import com.kal.beum.core.data.ApiConstants
import com.kal.beum.core.data.AppUserCache
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.notice.data.dto.NoticeCategoryDto
import com.kal.beum.notice.data.dto.NoticeDto
import com.kal.beum.notice.data.dto.NoticeMapDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText

class KtorRemoteNoticeDataSource(private val httpClient: HttpClient) : RemoteNoticeDataSource {

    override suspend fun getNoticeMap(): Result<NoticeMapDto, DataError.Remote> {
        val response = httpClient.post(ApiConstants.Endpoints.MY_NOTICE) {
            headers {
                AppUserCache.userInfo?.accessToken?.let {
                    append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                }
            }
        }

        return try {
            val noticeMapDto = response.body<NoticeMapDto>()
            Result.Success(noticeMapDto)
        } catch (e: Exception) {
            println("getNoticeMap parsing error: ${e.message}")
            Result.Error(DataError.Remote.REQUEST_ERROR)
        }
    }
}