package com.kal.beum.myinfo.data.repository

import com.kal.beum.core.data.AuthTokenCache
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.core.domain.map
import com.kal.beum.main.data.database.AppDao
import com.kal.beum.main.data.mappers.toUserInfo
import com.kal.beum.main.domain.UserInfo
import com.kal.beum.myinfo.data.network.RemoteMyInfoDataSource
import com.kal.beum.myinfo.data.toMyContent
import com.kal.beum.myinfo.domain.MyContent
import com.kal.beum.myinfo.domain.MyInfoRepository

class DefaultMyInfoRepository(
    private val appDao: AppDao, private val myInfoDataSource: RemoteMyInfoDataSource
) : MyInfoRepository {
    override suspend fun getMyInfo(): Result<UserInfo, DataError.Local> {
        val userInfo = appDao.getLoginInfo()?.toUserInfo()
        return if (userInfo != null) {
            AuthTokenCache.accessToken = userInfo.accessToken
            Result.Success(userInfo)
        } else {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun getMyContents(userId: Int): Result<List<MyContent>, DataError.Remote> {
        return myInfoDataSource.getMyContents(userId).map { it.map { item -> item.toMyContent() } }
    }

    override suspend fun getMyReply(userId: Int): Result<List<MyContent>, DataError.Remote> {
        return myInfoDataSource.getMyReply(userId).map { it.map { item -> item.toMyContent() } }
    }

    override suspend fun reportUser(myContent: MyContent): Result<Unit, DataError.Remote> {
        return myInfoDataSource.reportUser(myContent.id)
    }

}