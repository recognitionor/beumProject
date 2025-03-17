package com.kal.beum.home.data.network

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.home.data.dto.HomeCommentDto

interface RemoteHomeDataSource {
    suspend fun getHomeCommentList(isDevil: Boolean): Result<List<HomeCommentDto>, DataError.Remote>
}