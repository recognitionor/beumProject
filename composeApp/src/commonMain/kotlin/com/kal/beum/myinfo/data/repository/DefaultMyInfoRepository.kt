package com.kal.beum.myinfo.data.repository

import com.kal.beum.core.data.AuthTokenCache
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.core.domain.map
import com.kal.beum.core.domain.onError
import com.kal.beum.core.domain.onSuccess
import com.kal.beum.login.domain.SocialToken
import com.kal.beum.main.data.database.AppDao
import com.kal.beum.main.data.mappers.toUserInfo
import com.kal.beum.main.data.network.RemoteLoginDataSource
import com.kal.beum.main.domain.UserInfo
import com.kal.beum.myinfo.data.network.RemoteMyInfoDataSource
import com.kal.beum.myinfo.data.toMyContent
import com.kal.beum.myinfo.domain.MyContent
import com.kal.beum.myinfo.domain.MyInfoRepository

class DefaultMyInfoRepository(
    private val appDao: AppDao,
    private val myInfoDataSource: RemoteMyInfoDataSource
) : MyInfoRepository {
    override suspend fun getMyInfo(): Result<UserInfo, DataError.Local> {
        val userInfo = appDao.getLoginInfo()?.toUserInfo()
        if (userInfo == null) return Result.Error(DataError.Local.UNKNOWN)
        val result = myInfoDataSource.refreshLoginInfo(
            userInfo.socialType,
            SocialToken(userInfo.accessToken, userInfo.refreshToken)
        )
        return when (result) {
            is Result.Success -> {
                AuthTokenCache.accessToken = result.data.accessToken
                Result.Success(result.data)
            }
            is Result.Error -> Result.Error(DataError.Local.UNKNOWN)
            else -> Result.Progress()
        }
    }

    override suspend fun getMyContents(userId: Int): Result<List<MyContent>, DataError.Remote> =
        myInfoDataSource.getMyContents(userId).map { it.map { item -> item.toMyContent() } }

    override suspend fun getMyReply(userId: Int): Result<List<MyContent>, DataError.Remote> =
        myInfoDataSource.getMyReply(userId).map { it.map { item -> item.toMyContent() } }

    override suspend fun reportUser(myContent: MyContent): Result<Unit, DataError.Remote> =
        myInfoDataSource.reportUser(myContent.id)
}