package com.kal.beum.write.data.network

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.write.domain.WritingInfoRequest
import kotlinx.coroutines.delay

class MockWriteDataSource(
) : RemoteWriteDataSource {

    override suspend fun submitWriting(writingSubmitRequest: WritingInfoRequest): Result<Boolean, DataError.Remote> {
        delay(3000)
        return Result.Success(true)
    }
}