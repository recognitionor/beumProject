package com.kal.beum.home.domain

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result

interface HomeRepository {
    suspend fun getHomeCommentList(isDevil: Boolean): Result<List<HomeData>, DataError.Remote>
}