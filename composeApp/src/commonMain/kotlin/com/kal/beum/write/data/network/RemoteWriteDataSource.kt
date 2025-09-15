package com.kal.beum.write.data.network

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.write.domain.WritingInfoRequest

interface RemoteWriteDataSource {
    suspend fun submitWriting(writingSubmitRequest: WritingInfoRequest): Result<Boolean, DataError.Remote>
}