package com.kal.beum.main.data

import com.kal.beum.main.data.database.AppDao
import com.kal.beum.main.data.database.AppEntity
import com.kal.beum.main.data.database.UserInfoEntity
import com.kal.beum.main.data.mappers.toUserInfo
import com.kal.beum.main.data.network.RemoteLoginDataSource
import com.kal.beum.main.domain.AppRepository
import com.kal.beum.main.domain.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultAppRepository(
    private val appDao: AppDao, private val remoteLoginDataSource: RemoteLoginDataSource
) : AppRepository {
    override fun getLoginInfo(): Flow<UserInfo?> = flow {
        val result = appDao.getLoginInfo()?.toUserInfo()
        emit(result)
    }

    override fun login(socialType: Int): Flow<UserInfo> = flow {
        val loginInfo = appDao.getLoginInfo()
        if (loginInfo == null) {
            // 로그인 코드가 들어가야함
            val userInfo = UserInfoEntity(
                userId = "jhlee",
                nickName = "곡괭이",
                socialType = socialType,
                mail = "jhlee@gamil.com",
                sessionKey = ""
            )
            appDao.setLoginInfo(userInfo)
            emit(userInfo.toUserInfo())
        } else {
            emit(loginInfo.toUserInfo())
        }
    }

    override fun isOnBoardingDone(): Flow<Boolean> = flow {
        emit(appDao.getOnBoardingStatus())
    }

    override fun isDevil(): Flow<Boolean> = flow {
        emit(appDao.getAppEntity()?.isDevil == false)
    }

    override fun getAppEntity(): Flow<AppEntity> = flow {
        println("getAppEntity()")
        appDao.getAppEntity()?.let {
            emit(it)
        }
    }

    override suspend fun setIsDevil(isDevil: Boolean) {
        val appEntity: AppEntity? = appDao.getAppEntity()
        if (appEntity == null) {
            appDao.insertAppEntity(AppEntity(isOnBoardingDone = false, isDevil = isDevil))
        } else {
            appDao.updateIsDevil(isDevil = isDevil)
        }
    }

    override suspend fun setOnBoardingDone(isOnBoardingDone: Boolean) {
        println("setOnBoardingDone : $isOnBoardingDone")
        val appEntity: AppEntity? = appDao.getAppEntity()
        if (appEntity == null) {
            appDao.insertAppEntity(AppEntity(isOnBoardingDone = isOnBoardingDone, isDevil = false))
        } else {
            appDao.insertAppEntity(appEntity.copy(isOnBoardingDone = isOnBoardingDone))
        }
    }
}