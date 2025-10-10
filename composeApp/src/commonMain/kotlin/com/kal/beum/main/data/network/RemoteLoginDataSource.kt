package com.kal.beum.main.data.network

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.main.domain.UserInfo

interface RemoteLoginDataSource {
    suspend fun signup(
        socialType: Int,
        accessToken: String,
        refreshToken: String
    ): Result<UserInfo, DataError.Remote>

    suspend fun login(socialType: Int): Result<UserInfo, DataError.Remote>
    suspend fun logout(userInfo: UserInfo): Result<Unit, DataError.Remote>
    suspend fun withdraw(): Result<Unit, DataError.Remote>
    suspend fun updateFcmToken(userInfo: UserInfo, token: String)
    suspend fun refreshAccessToken(result: UserInfo): Result<UserInfo?, DataError.Remote>
}