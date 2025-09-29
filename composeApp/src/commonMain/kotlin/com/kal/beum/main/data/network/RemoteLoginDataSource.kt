package com.kal.beum.main.data.network

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.main.domain.UserInfo

interface RemoteLoginDataSource {
    suspend fun signup(socialType: Int, accessToken: String, refreshToken: String): Result<UserInfo, DataError.Remote>
    suspend fun login(socialType: Int): Result<UserInfo, DataError.Remote>
    suspend fun logout(userInfo: UserInfo): Result<Unit, DataError.Remote>
}