package com.kal.beum.myinfo.domain

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result

interface MyInfoRepository {
    suspend fun getMyInfo(): Result<MyInfo, DataError.Local>
    suspend fun getMyContents(userId: Int): Result<List<MyContent>, DataError.Remote>
    suspend fun getMyReply(userId: Int): Result<List<MyContent>, DataError.Remote>
    suspend fun reportUser(myContent: MyContent): Result<Unit, DataError.Remote>
}