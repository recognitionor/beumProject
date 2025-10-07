package com.kal.beum.content.domain

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result

interface ContentsRepository {

    suspend fun sendReply(
        boardId: Int,
        content: String,
        depth: Int,
        parentId: Int,
        devil: Boolean
    ): Result<Boolean, DataError.Remote>

    suspend fun getContentInfo(
        id: Int
    ): Result<ContentDetail, DataError.Remote>
}
