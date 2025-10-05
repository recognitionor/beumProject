package com.kal.beum.community.domain

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.write.domain.WritingData
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {

    suspend fun getTempWriting(): Result<WritingData, DataError.Local>
    suspend fun getCategoryList(): Result<List<Category>, DataError.Remote>
    suspend fun getCommunityList(
        page: Int, size: Int, isDevil: Boolean, category: Category
    ): Flow<Result<Community, DataError.Remote>>
}
