package com.kal.beum.write.data.network

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result

interface RemoteWriteDataSource {
    suspend fun submitWriting(): Result<Boolean, DataError.Remote>
}