package com.kal.beum.main.domain

import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getLoginInfo(): Flow<UserInfo?>
    fun login(socialType: Int): Flow<UserInfo>
    fun isOnBoardingDone(): Flow<Boolean>
    suspend fun setOnBoardingDone(isOnBoardingDone: Boolean)
}
