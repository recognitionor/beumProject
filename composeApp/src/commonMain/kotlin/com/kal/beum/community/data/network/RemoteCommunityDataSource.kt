package com.kal.beum.community.data.network

import com.kal.beum.community.data.dto.CategoryMapDto
import com.kal.beum.community.data.dto.CommunityDto
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result

interface RemoteCommunityDataSource {
    suspend fun getCategoryList(): Result<CategoryMapDto, DataError.Remote>

    suspend fun getCommunity(
        page: Int, size: Int, categoryId: Int, isDevil: Boolean
    ): Result<CommunityDto, DataError.Remote>

    suspend fun pickComment(
        targetUserId: String, boardId: String
    ): Result<Boolean, DataError.Remote>

}