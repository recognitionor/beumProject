//package com.kal.beum.main.data.network
//
//import com.kal.beum.core.domain.DataError
//import com.kal.beum.core.domain.Result
//import com.kal.beum.main.domain.UserInfo
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//
//class MockLoginDataSource : RemoteLoginDataSource {
//
//
//    override suspend fun signup(
//        socialType: Int,
//        accessToken: String,
//        refreshToken: String
//    ): Result<UserInfo, DataError.Remote> {
//        return Result.Success(
//            UserInfo(
//                userId = "",
//                nickName = "Mock User",
//                socialType = 1,
//                email = "",
//                sessionKey = "",
//                accessToken = "",
//                refreshToken = "",
//                profileImageId = "",
//                needSignUp = false
//            )
//        )
//    }
//
//    override suspend fun login(socialType: Int): Result<UserInfo, DataError.Remote> {
//        return Result.Success(
//            UserInfo(
//                userId = "",
//                nickName = "Mock User",
//                socialType = 1,
//                email = "",
//                sessionKey = "",
//                accessToken = "",
//                refreshToken = "",
//                profileImageId = "",
//                needSignUp = true
//            )
//        )
//    }
//
//    override suspend fun logout(userInfo: UserInfo): Result<Unit, DataError.Remote> {
//        return Result.Success(Unit)
//    }
//
//    override suspend fun updateFcmToken(
//        userInfo: UserInfo,
//        token: String
//    ) {
//    }
//}