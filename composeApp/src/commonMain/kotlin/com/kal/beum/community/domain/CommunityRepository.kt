package com.kal.beum.community.domain

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result

interface CommunityRepository {
    suspend fun getCategoryList(): Result<List<Category>, DataError.Remote>
    suspend fun getCommunityList(
        category: Category,
        isDevil: Boolean
    ): Result<List<CommunityItem>, DataError.Remote>
}
