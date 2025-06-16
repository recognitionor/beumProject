package com.kal.beum.write.data.network

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.write.data.CategoryGroupDto

interface RemoteWriteCategoryDataSource {
    suspend fun getWriteCategoryList(): Result<List<CategoryGroupDto>, DataError.Remote>
}