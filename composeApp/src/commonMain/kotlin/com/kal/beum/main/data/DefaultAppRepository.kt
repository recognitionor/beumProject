package com.kal.beum.main.data

import com.kal.beum.main.data.database.AppDao
import com.kal.beum.main.data.database.AppEntity
import com.kal.beum.main.domain.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultAppRepository(private val appDao: AppDao) : AppRepository {
    override fun isOnBoardingDone(): Flow<Boolean> = flow {
        emit(appDao.getOnBoardingStatus())
    }

    override suspend fun setOnBoardingDone(isOnBoardingDone: Boolean) {
        appDao.insertOnBoardingStatus(AppEntity(isOnBoardingDone = isOnBoardingDone))
    }
}