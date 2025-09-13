package com.kal.beum.main.data

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.core.domain.onError
import com.kal.beum.core.domain.onSuccess
import com.kal.beum.main.data.database.AppDao
import com.kal.beum.main.data.database.AppEntity
import com.kal.beum.main.data.database.UserInfoEntity
import com.kal.beum.main.data.mappers.toUserInfo
import com.kal.beum.main.data.network.RemoteLoginDataSource
import com.kal.beum.main.domain.AppRepository
import com.kal.beum.main.domain.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.onFailure
import kotlin.onSuccess

class DefaultAppRepository(
    private val appDao: AppDao, private val remoteLoginDataSource: RemoteLoginDataSource
) : AppRepository {
    override fun getLoginInfo(): Flow<UserInfo?> = flow {
        val result = appDao.getLoginInfo()?.toUserInfo()
        emit(result)
    }

    override fun signup(socialType: Int): Flow<Result<UserInfo, DataError.Remote>> = flow {
        emit(Result.Progress())
        val result = remoteLoginDataSource.login(socialType)
        result.onSuccess { userInfo ->
            val userInfoEntity = UserInfoEntity(
                userId = userInfo.userId,
                nickName = userInfo.nickName,
                socialType = userInfo.socialType,
                email = userInfo.email,
                accessToken = userInfo.accessToken,
                refreshToken = userInfo.refreshToken,
                sessionKey = userInfo.sessionKey,
                profileImageId = userInfo.profileImageId,
                needSignUp = userInfo.needSignUp

            )
            appDao.setLoginInfo(userInfoEntity)
            emit(result)
        }.onError {
            emit(result)
        }
    }

    override fun login(socialType: Int): Flow<Result<UserInfo, DataError.Remote>> = flow {
        emit(Result.Progress())
        val result = remoteLoginDataSource.login(socialType)
        result.onSuccess { userInfo ->
            val userInfoEntity = UserInfoEntity(
                userId = userInfo.userId,
                nickName = userInfo.nickName,
                socialType = userInfo.socialType,
                email = userInfo.email,
                accessToken = userInfo.accessToken,
                refreshToken = userInfo.refreshToken,
                sessionKey = userInfo.sessionKey,
                profileImageId = userInfo.profileImageId,
                needSignUp = userInfo.needSignUp

            )
            appDao.setLoginInfo(userInfoEntity)
            emit(result)
        }.onError {
            emit(result)
        }
    }

    override fun logout(userInfo: UserInfo): Flow<Result<Unit, DataError.Remote>> = flow {
        emit(Result.Progress())
        remoteLoginDataSource.logout(userInfo)
        appDao.clearLoginInfo()
        emit(Result.Success(Unit))
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