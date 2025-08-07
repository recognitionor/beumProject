package com.kal.beum.main.data.network

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.login.domain.LoginClient
import com.kal.beum.main.domain.SocialType
import com.kal.beum.main.domain.UserInfo

class SdkLoginDataSource(
    private val loginClients: Map<Int, LoginClient>
) : RemoteLoginDataSource {
    override suspend fun login(socialType: Int): Result<UserInfo, DataError.Remote> {
        println("test~~~~~~")
        loginClients.get(socialType)?.login(socialType) {
            println("login")
        }
        return Result.Success(UserInfo("1", "고라니", 1, "jhlee@gmail.com", "1111"))
    }
}

