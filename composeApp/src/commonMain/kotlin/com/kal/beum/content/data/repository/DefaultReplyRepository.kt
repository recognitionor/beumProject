package com.kal.beum.content.data.repository

import com.kal.beum.content.data.dto.CommentDetailDto
import com.kal.beum.content.data.dto.CommentRequestDto
import com.kal.beum.content.data.network.RemoteContentDataSource
import com.kal.beum.content.data.toCommentInfo
import com.kal.beum.content.data.toContentDetail
import com.kal.beum.content.domain.CommentDetail
import com.kal.beum.content.domain.CommentInfo
import com.kal.beum.content.domain.ReplyRepository
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.core.domain.onError
import com.kal.beum.core.domain.onProgress
import com.kal.beum.core.domain.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultReplyRepository(private val remoteContentDataSource: RemoteContentDataSource) :
    ReplyRepository {

    override suspend fun sendReply(
        boardId: Int, content: String, depth: Int, parentId: Int?, devil: Boolean
    ): Flow<Result<CommentDetail, DataError.Remote>> = flow {
        emit(Result.Progress())
        val result = remoteContentDataSource.sendReply(
            CommentRequestDto(
                boardId, content, depth, parentId, devil
            )
        )
        result.onSuccess {
            emit(Result.Success(it.toContentDetail())) }.onError {
            emit(Result.Error(DataError.Remote.REQUEST_ERROR))
        }
    }

    override suspend fun getReplyList(
        boardId: Int,
        commentId: Int?
    ): Result<CommentInfo, DataError.Remote> {
        remoteContentDataSource.getReply(boardId, commentId)
            .onSuccess { commentInfoDto ->
                return Result.Success(commentInfoDto.toCommentInfo())
            }
            .onError {
                return Result.Error(it)
            }
        return Result.Error(DataError.Remote.REQUEST_ERROR)
    }

    override suspend fun likeReply(commentInfo: CommentDetail): Result<CommentDetail, DataError.Remote> {
        println("likeReply start : $commentInfo")
        remoteContentDataSource.likeComment(commentInfo.id).onSuccess {
            val contentDetailTemp = commentInfo.copy(
                likeIsMe = !commentInfo.likeIsMe,
                likeCount = if (commentInfo.likeIsMe) commentInfo.likeCount - 1 else commentInfo.likeCount + 1
            )
            println("likeReply  end : $contentDetailTemp")
            return Result.Success(contentDetailTemp)
        }
        return Result.Success(commentInfo)
    }
}