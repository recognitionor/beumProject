package com.kal.beum.write.data.repository

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.write.data.network.RemoteWriteDataSource
import com.kal.beum.write.domain.WritingRepository

class DefaultWritingRepository(private val remoteWriteDataSource: RemoteWriteDataSource) :
    WritingRepository {

    override suspend fun submitWriting(): Result<Boolean, DataError.Remote> {
        return remoteWriteDataSource.submitWriting()
    }
}