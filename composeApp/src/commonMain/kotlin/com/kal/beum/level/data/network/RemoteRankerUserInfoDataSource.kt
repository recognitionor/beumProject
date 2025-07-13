package com.kal.beum.level.data.network

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.level.data.dto.RankerUserInfoDto

interface RemoteRankerUserInfoDataSource {
    suspend fun getRankerList(isDevel: Boolean): Result<List<RankerUserInfoDto>, DataError.Remote>
}