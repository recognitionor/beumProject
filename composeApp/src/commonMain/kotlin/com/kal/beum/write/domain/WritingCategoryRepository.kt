package com.kal.beum.write.domain

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result

interface WritingCategoryRepository {
    suspend fun getWriteCategory(): Result<Map<String, List<WritingCategory>>, DataError.Remote>
}