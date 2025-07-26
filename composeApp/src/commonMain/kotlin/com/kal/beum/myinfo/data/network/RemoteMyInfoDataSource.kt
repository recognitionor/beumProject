package com.kal.beum.myinfo.data.network

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.myinfo.data.dto.MyContentDto

interface RemoteMyInfoDataSource {
    suspend fun getMyContents(userId: Int): Result<List<MyContentDto>, DataError.Remote>

    suspend fun getMyReply(userId: Int): Result<List<MyContentDto>, DataError.Remote>

    suspend fun reportUser(myContentId: Int): Result<Unit, DataError.Remote>
}