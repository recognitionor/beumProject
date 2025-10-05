package com.kal.beum.community.data.network

import com.kal.beum.community.data.dto.CategoryDto
import com.kal.beum.community.data.dto.CommunityDto
import com.kal.beum.community.domain.Community
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result

interface RemoteCommunityDataSource {
    suspend fun getCategoryList(): Result<List<CategoryDto>, DataError.Remote>

    suspend fun getCommunity(
        page: Int, size: Int, categoryId: Int, isDevil: Boolean
    ): Result<CommunityDto, DataError.Remote>

}