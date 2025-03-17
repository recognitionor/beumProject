package com.kal.beum.home.data.repository

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.core.domain.map
import com.kal.beum.home.data.network.RemoteHomeDataSource
import com.kal.beum.home.data.toHomeData
import com.kal.beum.home.domain.HomeData
import com.kal.beum.home.domain.HomeRepository

class DefaultHomeRepository(private val remoteHomeDataSource: RemoteHomeDataSource) :
    HomeRepository {
    override suspend fun getHomeCommentList(isDevil: Boolean): Result<List<HomeData>, DataError.Remote> {
        return remoteHomeDataSource.getHomeCommentList(isDevil).map {
            it.map { item -> item.toHomeData() }
        }
    }
}