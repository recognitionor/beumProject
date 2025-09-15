package com.kal.beum.write.data.network

import com.kal.beum.core.data.ApiConstants
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
        val response = httpClient.post(ApiConstants.Endpoints.BOARD) {
            setBody(
                BoardSubmitRequest(
                    boardReq = writingSubmitRequest,
                    userDto = WritingUserDto(userId = 6, nickname = "KAL")
                )
            )
        }
        println(response)
        return Result.Success(true)
    }
}