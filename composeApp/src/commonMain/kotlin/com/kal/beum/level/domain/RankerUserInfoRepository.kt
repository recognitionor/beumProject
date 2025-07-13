package com.kal.beum.level.domain

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result

interface RankerUserInfoRepository {
    suspend fun getRankerList(isDevel: Boolean): Result<List<RankerUserInfo>, DataError.Remote>
}