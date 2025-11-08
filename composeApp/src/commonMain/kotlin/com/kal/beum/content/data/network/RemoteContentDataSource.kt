package com.kal.beum.content.data.network

import com.kal.beum.content.data.dto.BoardDetailDto
import com.kal.beum.content.data.dto.CommentDetailDto
import com.kal.beum.content.data.dto.CommentInfoDto
import com.kal.beum.content.data.dto.CommentRequestDto
import com.kal.beum.content.data.dto.ReportRequestDto
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result

interface RemoteContentDataSource {
    suspend fun getContentDetail(contentId: Int): Result<BoardDetailDto, DataError.Remote>
    suspend fun getReply(
        boardId: Int, commentId: Int? = null
    ): Result<CommentInfoDto, DataError.Remote>

    suspend fun sendReply(commentDto: CommentRequestDto): Result<CommentDetailDto, DataError.Remote>
    suspend fun likeBoard(boardId: Int): Result<Boolean, DataError.Remote>

    suspend fun likeComment(replyId: Int): Result<Boolean, DataError.Remote>

    suspend fun reportContent(reportRequestDto: ReportRequestDto): Result<Boolean, DataError.Remote>
}