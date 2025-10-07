package com.kal.beum.community.data.repository

import com.kal.beum.community.data.network.RemoteCommunityDataSource
import com.kal.beum.community.data.toCategoryData
import com.kal.beum.community.data.toCommunity
import com.kal.beum.community.data.toCommunityItem
import com.kal.beum.community.domain.Category
import com.kal.beum.community.domain.Community
import com.kal.beum.community.domain.CommunityItem
import com.kal.beum.community.domain.CommunityRepository
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.core.domain.map
import com.kal.beum.core.domain.onError
import com.kal.beum.core.domain.onSuccess
import com.kal.beum.write.data.database.WritingDao
import com.kal.beum.write.data.toWritingData
import com.kal.beum.write.domain.WritingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultCommunityRepository(
    private val writingDao: WritingDao,
    private val remoteCommunityDataSource: RemoteCommunityDataSource
) : CommunityRepository {
    override suspend fun getTempWriting(): Result<WritingData, DataError.Local> {
        val entity = writingDao.getWritingById(1)
        return if (entity == null) {
            Result.Error(DataError.Local.EMPTY_TEMP_WRITING)
        } else {
            Result.Success(entity.toWritingData())
        }
    }

    override suspend fun getCategoryList(): Result<List<Category>, DataError.Remote> {
        val test = remoteCommunityDataSource.getCategoryList().map { it.map { item -> item.toCategoryData() } }
        return remoteCommunityDataSource.getCategoryList()
            .map { it.map { item -> item.toCategoryData() } }
    }

    override suspend fun getCommunityList(
        page: Int, size: Int, isDevil: Boolean, category: Category
    ): Flow<Result<Community, DataError.Remote>> = flow {
        emit(Result.Progress())
        val result = remoteCommunityDataSource.getCommunity(page, size, category.id, isDevil)
        result.onSuccess {
            emit(Result.Success(it.toCommunity()))
        }.onError {
            emit(Result.Error(DataError.Remote.REQUEST_ERROR))
        }
    }
}