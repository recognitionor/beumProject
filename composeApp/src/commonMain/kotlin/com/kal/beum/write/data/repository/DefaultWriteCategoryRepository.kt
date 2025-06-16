package com.kal.beum.write.data.repository

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.core.domain.map
import com.kal.beum.write.data.network.RemoteWriteCategoryDataSource
import com.kal.beum.write.domain.WritingCategory
import com.kal.beum.write.domain.WritingCategoryRepository

class DefaultWriteCategoryRepository(private val remoteWriteCategoryDataSource: RemoteWriteCategoryDataSource) :
    WritingCategoryRepository {

    override suspend fun getWriteCategory(): Result<Map<String, List<WritingCategory>>, DataError.Remote> {
        return remoteWriteCategoryDataSource.getWriteCategoryList().map { dtoList ->
            dtoList.groupBy { it.groupName } // 여기서 category가 빅카테고리!
                .mapValues { entry ->
                    entry.value.map { dto ->
                        WritingCategory(
                            categoryId = dto.id, category = dto.category
                        )
                    }
                }
        }
    }
}