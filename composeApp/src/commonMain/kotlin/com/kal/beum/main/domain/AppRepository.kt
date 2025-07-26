package com.kal.beum.main.domain

import com.kal.beum.main.data.database.AppEntity
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getLoginInfo(): Flow<UserInfo?>
    fun login(socialType: Int): Flow<UserInfo>

    fun logout(): Flow<Unit>
    fun isOnBoardingDone(): Flow<Boolean>
    fun isDevil(): Flow<Boolean>
    fun getAppEntity(): Flow<AppEntity>
    suspend fun setIsDevil(isDevil: Boolean)
    suspend fun setOnBoardingDone(isOnBoardingDone: Boolean)
}
