package com.kal.beum.community.data.repository

import com.kal.beum.community.data.network.RemoteCommunityDataSource
import com.kal.beum.community.data.toCategoryData
import com.kal.beum.community.data.toCommunityItem
import com.kal.beum.community.domain.Category
import com.kal.beum.community.domain.CommunityItem
import com.kal.beum.community.domain.CommunityRepository
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.core.domain.map
import com.kal.beum.write.data.database.WritingDao
import com.kal.beum.write.data.toWritingData
import com.kal.beum.write.domain.WritingData

class DefaultCommunityRepository(
    private val writingDao: WritingDao,
    private val remoteCommunityDataSource: RemoteCommunityDataSource
) :
    CommunityRepository {
    override suspend fun getTempWriting(): Result<WritingData, DataError.Local> {
        val entity = writingDao.getWritingById(1)
        return if (entity == null) {
            Result.Error(DataError.Local.EMPTY_TEMP_WRITING)
        } else {
            println("getTempWriting : $entity")
            Result.Success(entity.toWritingData())
        }
    }

    override suspend fun getCategoryList(): Result<List<Category>, DataError.Remote> {
        return remoteCommunityDataSource.getCategoryList()
            .map { it.map { item -> item.toCategoryData() } }
    }

    override suspend fun getCommunityList(
        category: Category, isDevil: Boolean
    ): Result<List<CommunityItem>, DataError.Remote> {
        return remoteCommunityDataSource.getCommunityList(category.id, isDevil)
            .map { it.map { item -> item.toCommunityItem() } }
    }
}