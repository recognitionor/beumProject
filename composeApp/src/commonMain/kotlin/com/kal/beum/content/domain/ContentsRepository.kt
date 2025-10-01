package com.kal.beum.content.domain

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result

interface ContentsRepository {

    suspend fun sendReply(
        id: Int,
        reply: String
    ): Result<Boolean, DataError.Remote>

    suspend fun getContentInfo(
        id: Int
    ): Result<ContentDetail, DataError.Remote>
}
