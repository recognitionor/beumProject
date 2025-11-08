package com.kal.beum.content.domain

import com.kal.beum.content.data.dto.CommentDetailDto
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface ReplyRepository {
    suspend fun sendReply(
        boardId: Int,
        content: String,
        depth: Int,
        parentId: Int?,
        devil: Boolean
    ): Flow<Result<CommentDetail, DataError.Remote>>

    suspend fun getReplyList(
        boardId: Int, commentId: Int? = null
    ): Result<CommentInfo, DataError.Remote>

    suspend fun likeReply(
        commentInfo: CommentDetail
    ): Result<CommentDetail, DataError.Remote>

}
