package com.kal.beum.main.data.network

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.main.domain.UserInfo

class MockLoginDataSource : RemoteLoginDataSource {
    override suspend fun login(socialType: Int): Result<UserInfo, DataError.Remote> {
        return Result.Success(UserInfo("1", "고라니", 1, "jhlee@gmail.com", "1111"))
    }
}