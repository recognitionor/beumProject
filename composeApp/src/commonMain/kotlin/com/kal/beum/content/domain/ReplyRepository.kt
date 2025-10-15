package com.kal.beum.content.domain

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface ReplyRepository {
    suspend fun sendReply(
        commentInfo: CommentInfo
    ): Flow<Result<Boolean, DataError.Remote>>

    suspend fun getReplyList(
        contentId: Int
    ): Result<CommentInfo, DataError.Remote>

    suspend fun likeReply(
        commentInfo: CommentDetail
    ): Result<CommentDetail, DataError.Remote>

}
