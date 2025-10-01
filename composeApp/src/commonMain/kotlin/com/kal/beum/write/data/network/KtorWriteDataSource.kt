package com.kal.beum.write.data.network

import com.kal.beum.core.data.ApiConstants
import com.kal.beum.core.data.AuthTokenCache
import com.kal.beum.core.data.safeCall
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.write.domain.BoardSubmitRequest
import com.kal.beum.write.domain.WritingInfoRequest
import com.kal.beum.write.domain.WritingUserDto
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class KtorWriteDataSource(private val httpClient: HttpClient) : RemoteWriteDataSource {

    override suspend fun submitWriting(writingSubmitRequest: WritingInfoRequest): Result<Boolean, DataError.Remote> {
        println("AuthTokenCache.accessToken : ${AuthTokenCache.accessToken}")
        println("AuthTokenCache.writingSubmitRequest : $writingSubmitRequest")
        val response = httpClient.post(ApiConstants.Endpoints.BOARD) {

            headers {
                AuthTokenCache.accessToken?.let {
                    append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                }
            }
            setBody(
                writingSubmitRequest
            )
        }
        return if (response.status.value == 200) {
            Result.Success(true)
        } else {
            Result.Error(DataError.Remote.FAILED_BOARD)
        }
    }
}