package com.kal.beum.content.data.repository

import com.kal.beum.content.data.dto.CommentRequestDto
import com.kal.beum.content.data.network.RemoteContentDataSource
import com.kal.beum.content.data.toCommentInfo
import com.kal.beum.content.domain.ContentDetail
import com.kal.beum.content.domain.ContentsRepository
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.utils.stringTimeToLong

class DefaultContentDetailRepository(private val remoteContentDataSource: RemoteContentDataSource) :
    ContentsRepository {


    override suspend fun sendReply(
        boardId: Int,
        content: String,
        depth: Int,
        parentId: Int,
        devil: Boolean
    ): Result<Boolean, DataError.Remote> {
        val commentDto = CommentRequestDto(boardId = boardId, content = content, depth = depth, parentId = parentId, devil = devil)
        return remoteContentDataSource.sendReply(commentDto)
    }

    override suspend fun getContentInfo(
        id: Int
    ): Result<ContentDetail, DataError.Remote> {
        println("getContentInfo")
        // 1. ContentDetail 정보 가져오기
        val contentResult = remoteContentDataSource.getContentDetail(id)
        if (contentResult is Result.Error) {
            return Result.Error(contentResult.error)
        }
        val content = (contentResult as Result.Success).data

        // 2. Reply 정보 가져오기
        val replyResult = remoteContentDataSource.getReply(id)

        if (replyResult is Result.Error) {
            return Result.Error(replyResult.error)
        }
        val replyInfo = (replyResult as Result.Success).data
        val contentDetail = ContentDetail(
            id = content.id,
            title = content.title,
            content = content.content,
            writer = content.writer,
            isDevil = content.boardType,
            categoryName = content.categoryName,
            rewardPoint = 0,
            tags = "",
            likeCount = content.likeCount,
            viewCount = content.viewCount,
            lastModifiedTime = stringTimeToLong(content.createTime),
            commentInfo = replyInfo.toCommentInfo(),
        )
        return Result.Success(contentDetail)
    }
}