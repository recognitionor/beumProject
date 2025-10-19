package com.kal.beum.write.data.repository

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.core.domain.onSuccess
import com.kal.beum.write.data.database.WritingDao
import com.kal.beum.write.data.database.WritingEntity
import com.kal.beum.write.data.network.RemoteWriteDataSource
import com.kal.beum.write.domain.WritingCategory
import com.kal.beum.write.domain.WritingRepository
import com.kal.beum.write.domain.WritingInfoRequest

class DefaultWritingRepository(
    private val writingDao: WritingDao, private val remoteWriteDataSource: RemoteWriteDataSource
) : WritingRepository {

    override suspend fun submitWriting(writingSubmitRequest: WritingInfoRequest): Result<Boolean, DataError.Remote> {
        val result = remoteWriteDataSource.submitWriting(writingSubmitRequest)
        result.onSuccess {
            if (it) {
                writingDao.deleteWritingById(1)
            }
        }
        return result
    }

    override suspend fun clearTempWritingTitle(): Result<Boolean, DataError.Local> {
        writingDao.deleteWritingById(1)
        return Result.Success(true)
    }

    override suspend fun saveTempWritingTitle(title: String): Result<Boolean, DataError.Local> {
        val writingEntity = writingDao.getWritingById(1)
        if (writingEntity == null) {
            writingDao.insertOrReplaceWriting(
                WritingEntity(
                    id = 1,
                    title = title,
                    content = "",
                    tags = "",
                    isDevil = false,
                    categoryId = 0,
                    category = "",
                    rewardPoint = 0
                )
            )
        } else {
            writingDao.updateTitle(writingEntity.id, title)
        }
        return Result.Success(true)
    }

    override suspend fun saveTempWritingContent(content: String): Result<Boolean, DataError.Local> {
        val writingEntity = writingDao.getWritingById(1)
        if (writingEntity == null) {
            writingDao.insertOrReplaceWriting(
                WritingEntity(
                    id = 1,
                    title = "",
                    content = content,
                    tags = "",
                    isDevil = false,
                    categoryId = 0,
                    category = "",
                    rewardPoint = 0
                )
            )
        } else {
            writingDao.updateContent(writingEntity.id, content)
        }
        return Result.Success(true)
    }

    override suspend fun saveTempWritingTags(tags: String): Result<Boolean, DataError.Local> {
        val writingEntity = writingDao.getWritingById(1)
        if (writingEntity == null) {
            writingDao.insertOrReplaceWriting(
                WritingEntity(
                    id = 1,
                    title = "",
                    content = "",
                    tags = tags,
                    isDevil = false,
                    categoryId = 0,
                    category = "",
                    rewardPoint = 0
                )
            )
        } else {
            writingDao.updateTags(writingEntity.id, tags)
        }
        return Result.Success(true)
    }

    override suspend fun saveTempWritingIsDevil(isDevil: Boolean): Result<Boolean, DataError.Local> {
        val writingEntity = writingDao.getWritingById(1)
        if (writingEntity == null) {
            writingDao.insertOrReplaceWriting(
                WritingEntity(
                    id = 1,
                    title = "",
                    content = "",
                    tags = "",
                    isDevil = isDevil,
                    categoryId = 0,
                    category = "",
                    rewardPoint = 0
                )
            )
        } else {
            writingDao.updateIsDevil(writingEntity.id, isDevil)
        }
        return Result.Success(true)
    }

    override suspend fun saveTempWritingRewardPoint(rewardPoint: Int): Result<Boolean, DataError.Local> {
        val writingEntity = writingDao.getWritingById(1)
        if (writingEntity == null) {
            writingDao.insertOrReplaceWriting(
                WritingEntity(
                    id = 1,
                    title = "",
                    content = "",
                    tags = "",
                    isDevil = false,
                    categoryId = 0,
                    category = "",
                    rewardPoint = rewardPoint
                )
            )
        } else {
            writingDao.updateRewardPoint(writingEntity.id, rewardPoint)
        }
        return Result.Success(true)
    }

    override suspend fun saveTempWritingCategory(writingCategory: WritingCategory): Result<Boolean, DataError.Local> {
        val writingEntity = writingDao.getWritingById(1)
        if (writingEntity == null) {
            writingDao.insertOrReplaceWriting(
                WritingEntity(
                    id = 1,
                    title = "",
                    content = "",
                    tags = "",
                    isDevil = false,
                    categoryId = writingCategory.categoryId,
                    category = writingCategory.category,
                    rewardPoint = 0
                )
            )
        } else {
            writingDao.updateCategoryInfo(
                writingEntity.id,
                writingCategory.categoryId,
                writingCategory.category
            )
        }
        return Result.Success(true)
    }
}