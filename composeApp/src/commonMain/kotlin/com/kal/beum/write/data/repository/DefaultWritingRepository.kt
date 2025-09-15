package com.kal.beum.write.data.repository

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.write.data.network.RemoteWriteDataSource
import com.kal.beum.write.domain.WritingRepository
import com.kal.beum.write.domain.WritingInfoRequest

class DefaultWritingRepository(private val remoteWriteDataSource: RemoteWriteDataSource) :
    WritingRepository {

    override suspend fun submitWriting(writingSubmitRequest: WritingInfoRequest): Result<Boolean, DataError.Remote> {
        return remoteWriteDataSource.submitWriting(writingSubmitRequest)
    }
}