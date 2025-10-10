package com.kal.beum.content.data.repository

import com.kal.beum.content.data.dto.CommentRequestDto
import com.kal.beum.content.data.network.RemoteContentDataSource
import com.kal.beum.content.domain.CommentInfo
import com.kal.beum.content.domain.ReplyRepository
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result

class DefaultReplyRepository(private val remoteContentDataSource: RemoteContentDataSource) :
    ReplyRepository {
    override suspend fun sendReply(
        boardId: Int, content: String, depth: Int, parentId: Int?, devil: Boolean
    ): Result<Boolean, DataError.Remote> {
        println("sendReply")
        val commentDto = CommentRequestDto(
            boardId = boardId, content = content, depth = depth, parentId = parentId, devil = devil
        )
        remoteContentDataSource.sendReply(commentDto)
        return Result.Error(DataError.Remote.REQUEST_ERROR)
    }

    override suspend fun getReplyList(contentId: Int): Result<CommentInfo, DataError.Remote> {
        println("getReplyList")
        remoteContentDataSource.getReply(contentId)
        return Result.Error(DataError.Remote.REQUEST_ERROR)
    }
}