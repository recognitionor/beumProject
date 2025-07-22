package com.kal.beum.myinfo.data.repository

import com.kal.beum.community.data.toCommunityItem
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.core.domain.map
import com.kal.beum.myinfo.data.database.MockMyInfoDao
import com.kal.beum.myinfo.data.database.MyInfoDao
import com.kal.beum.myinfo.data.network.RemoteMyInfoDataSource
import com.kal.beum.myinfo.data.toMyContent
import com.kal.beum.myinfo.domain.MyContent
import com.kal.beum.myinfo.domain.MyInfo
import com.kal.beum.myinfo.domain.MyInfoRepository

class DefaultMyInfoRepository(
    private val myInfoDao: MyInfoDao, private val myInfoDataSource: RemoteMyInfoDataSource
) : MyInfoRepository {
    override suspend fun getMyInfo(): Result<MyInfo, DataError.Local> {
        return Result.Success(myInfoDao.getMyInfo())
    }

    override suspend fun getMyContents(userId: Int): Result<List<MyContent>, DataError.Remote> {
        return myInfoDataSource.getMyContents(userId).map { it.map { item -> item.toMyContent() } }
    }

    override suspend fun getMyReply(userId: Int): Result<List<MyContent>, DataError.Remote> {
        return myInfoDataSource.getMyReply(userId).map { it.map { item -> item.toMyContent() } }
    }

}