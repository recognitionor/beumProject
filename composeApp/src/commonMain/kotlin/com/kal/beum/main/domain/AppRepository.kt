package com.kal.beum.main.domain

import com.kal.beum.core.domain.*
import com.kal.beum.main.data.database.AppEntity
import com.kal.beum.write.domain.WritingData
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getLoginInfo(): Flow<UserInfo?>

    fun signup(
        socialType: Int, accessToken: String, refreshToken: String
    ): Flow<Result<UserInfo, DataError.Remote>>

    fun login(socialType: Int): Flow<Result<UserInfo, DataError.Remote>>
    fun logout(userInfo: UserInfo): Flow<Result<Unit, DataError.Remote>>
    fun withdraw(): Flow<Result<Unit, DataError.Remote>>
    fun isOnBoardingDone(): Flow<Boolean>
    fun isDevil(): Flow<Boolean>
    fun getAppEntity(): Flow<AppEntity>
    suspend fun setIsDevil(isDevil: Boolean)
    suspend fun setOnBoardingDone(isOnBoardingDone: Boolean)
    suspend fun getTempWriting(): Result<WritingData, DataError.Local>
}
