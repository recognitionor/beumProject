package com.kal.beum.content.domain

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface ContentsRepository {

    suspend fun sendReply(
        boardId: Int, content: String, depth: Int, parentId: Int?, devil: Boolean
    ): Flow<Result<CommentDetail, DataError.Remote>>

    suspend fun getContentInfo(
        id: Int
    ): Flow<Result<ContentDetail, DataError.Remote>>

    suspend fun reportContent(
        boardId: Int, reportContent: String, reportId: Int, reportType: String, reportedUserId: Int
    ): Flow<Result<Boolean, DataError.Remote>>

    suspend fun reportUser(
        boardId: Int, reportContent: String, reportId: Int, reportType: String, reportedUserId: Int
    ): Flow<Result<Boolean, DataError.Remote>>

    suspend fun likeBoardToggle(
        contentDetail: ContentDetail
    ): Result<ContentDetail, DataError.Remote>

    suspend fun likeCommentToggle(
        commentDetail: CommentDetail
    ): Result<CommentDetail, DataError.Remote>

    suspend fun pickComment(targetUserId: String, boardId: String): Result<Boolean, DataError.Remote>

}
