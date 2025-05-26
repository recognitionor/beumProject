package com.kal.beum.community.data.network

import com.kal.beum.community.data.dto.CategoryDto
import com.kal.beum.community.data.dto.CommunityItemDto
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result

interface RemoteCommunityDataSource {
    suspend fun getCategoryList(): Result<List<CategoryDto>, DataError.Remote>

    suspend fun getCommunityList(categoryId: Int, isDevil:Boolean): Result<List<CommunityItemDto>, DataError.Remote>

}