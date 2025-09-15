package com.kal.beum.myinfo.domain

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.main.domain.UserInfo

interface MyInfoRepository {
    suspend fun getMyInfo(): Result<UserInfo, DataError.Local>
    suspend fun getMyContents(userId: Int): Result<List<MyContent>, DataError.Remote>
    suspend fun getMyReply(userId: Int): Result<List<MyContent>, DataError.Remote>
    suspend fun reportUser(myContent: MyContent): Result<Unit, DataError.Remote>
}