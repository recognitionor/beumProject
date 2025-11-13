package com.kal.beum.main.data

import com.kal.beum.core.data.AppUserCache
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
import com.kal.beum.write.data.database.WritingDao
import com.kal.beum.write.data.toWritingData
import com.kal.beum.write.domain.WritingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultAppRepository(
    private val appDao: AppDao,
    private val writingDao: WritingDao,
    private val remoteLoginDataSource: RemoteLoginDataSource
) : AppRepository {

    override fun getLoginInfo(): Flow<UserInfo?> = flow {
        // 캐싱된 로그인 정보 가져 오기
        var result = appDao.getLoginInfo()?.toUserInfo()
        AppUserCache.userInfo = result
        if (result != null) {
            val refreshedUserInfo = remoteLoginDataSource.refreshAccessToken(result)
            println("한번에 : $refreshedUserInfo")
            refreshedUserInfo.onSuccess {
                result = it
            }
        } else {
            emit(null)
        }
        result?.let {
            println("setLoginInfo accessToken : ${it.accessToken}")
            println("setLoginInfo refreshToken : ${it.refreshToken}")
            appDao.setLoginInfo(
                UserInfoEntity(
                    userId = it.userId,
                    nickName = it.nickName,
                    socialType = it.socialType,
                    email = it.email,
                    accessToken = it.accessToken,
                    refreshToken = it.refreshToken,
                    sessionKey = it.sessionKey,
                    profileImageId = it.profileImageId,
                    needSignUp = it.needSignUp,
                    angelPoint = it.angelPoint,
                    devilPoint = it.devilPoint
                )
            )
            if (AppUserCache.updateFCMToken != null) {
                remoteLoginDataSource.updateFcmToken(result!!, AppUserCache.updateFCMToken!!)
                AppUserCache.updateFCMToken = null
            }
        }
        if (result == null) {
            appDao.clearLoginInfo()
            AppUserCache.userInfo = null
        }
        emit(result)
    }

    override fun signup(
        socialType: Int, accessToken: String, refreshToken: String
    ): Flow<Result<UserInfo, DataError.Remote>> = flow {
        emit(Result.Progress())
        val result = remoteLoginDataSource.signup(socialType, accessToken, refreshToken)
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
                needSignUp = userInfo.needSignUp,
                angelPoint = userInfo.angelPoint,
                devilPoint = userInfo.devilPoint
            )
            appDao.setLoginInfo(userInfoEntity)
            AppUserCache.userInfo = userInfo
            val fcmToken = AppUserCache.updateFCMToken
            if (fcmToken != null) {
                remoteLoginDataSource.updateFcmToken(userInfo, fcmToken)
                AppUserCache.updateFCMToken = null
            }
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
                needSignUp = userInfo.needSignUp,
                angelPoint = userInfo.angelPoint,
                devilPoint = userInfo.devilPoint
            )
            appDao.setLoginInfo(userInfoEntity)
            AppUserCache.userInfo = userInfo
            val fcmToken = AppUserCache.updateFCMToken
            if (fcmToken != null) {
                remoteLoginDataSource.updateFcmToken(userInfo, fcmToken)
                AppUserCache.updateFCMToken = null
            }
            emit(result)
        }.onError {
            emit(result)
        }
    }

    override fun logout(userInfo: UserInfo): Flow<Result<Unit, DataError.Remote>> = flow {
        emit(Result.Progress())
        remoteLoginDataSource.logout(userInfo)
        appDao.clearLoginInfo()
        AppUserCache.userInfo = null
        emit(Result.Success(Unit))
    }

    override fun withdraw(): Flow<Result<Unit, DataError.Remote>> = flow {
        emit(Result.Progress())
        val result = remoteLoginDataSource.withdraw()
        AppUserCache.userInfo = null
        appDao.clearLoginInfo()
        result.onSuccess {
            emit(Result.Success(Unit))
        }.onError {
            emit(Result.Error(DataError.Remote.REQUEST_ERROR))
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

    override suspend fun getTempWriting(): Result<WritingData, DataError.Local> {
        val entity = writingDao.getWritingById(1)
        return if (entity == null) {
            Result.Error(DataError.Local.EMPTY_TEMP_WRITING)
        } else {
            println("getTempWriting : $entity")
            Result.Success(entity.toWritingData())
        }
    }
}