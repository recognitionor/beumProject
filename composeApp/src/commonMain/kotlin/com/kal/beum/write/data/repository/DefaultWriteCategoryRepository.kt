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
        return remoteWriteCategoryDataSource.getWriteCategoryList().map { categoryMapDto ->
            // categoryMapDto.categoryMap: Map<String, List<CategoryDto>>
            categoryMapDto.categoryMap.mapValues { (_, categoryDtoList) ->
                categoryDtoList.map { dto ->
                    WritingCategory(
                        categoryId = dto.id,
                        category = dto.name  // 또는 dto.category (WriteCategoryDto 필드명에 따라)
                    )
                }
            }
        }
    }
}