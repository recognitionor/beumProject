package com.kal.beum.main.domain

import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun isOnBoardingDone(): Flow<Boolean>
    suspend fun setOnBoardingDone(isOnBoardingDone: Boolean)
}
