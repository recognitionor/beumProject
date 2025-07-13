package com.kal.beum.level.data.repository

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.core.domain.map
import com.kal.beum.level.data.network.MockRankerUserInfoDataSource
import com.kal.beum.level.data.network.RemoteRankerUserInfoDataSource
import com.kal.beum.level.data.toRankerUserInfo
import com.kal.beum.level.domain.RankerUserInfo
import com.kal.beum.level.domain.RankerUserInfoRepository

class DefaultRankerUserInfoRepository(private val remoteCommunityDataSource: RemoteRankerUserInfoDataSource) :
    RankerUserInfoRepository {
    override suspend fun getRankerList(isDevel: Boolean): Result<List<RankerUserInfo>, DataError.Remote> {
        remoteCommunityDataSource.getRankerList(isDevel).map { rankerUserInfoDtoList ->
            rankerUserInfoDtoList.map { it.toRankerUserInfo() }
        }.let { result ->
            return when (result) {
                is Result.Success -> Result.Success(result.data)
                is Result.Error -> Result.Error(DataError.Remote.UNKNOWN)
            }
        }
    }
}