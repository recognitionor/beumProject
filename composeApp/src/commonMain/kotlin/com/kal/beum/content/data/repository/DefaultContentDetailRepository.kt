package com.kal.beum.content.data.repository

import com.kal.beum.content.data.dto.CommentRequestDto
import com.kal.beum.content.data.dto.CommentUserDto
import com.kal.beum.content.data.network.RemoteContentDataSource
import com.kal.beum.content.data.toCommentInfo
import com.kal.beum.content.domain.CommentDetail
import com.kal.beum.content.domain.ContentDetail
import com.kal.beum.content.domain.ContentsRepository
import com.kal.beum.core.data.AppUserCache
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.core.domain.onError
import com.kal.beum.core.domain.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock

class DefaultContentDetailRepository(private val remoteContentDataSource: RemoteContentDataSource) :
    ContentsRepository {


    override suspend fun sendReply(
        boardId: Int, content: String, depth: Int, parentId: Int?, devil: Boolean
    ): Flow<Result<CommentDetail, DataError.Remote>> = flow {
        println("sendReply@@@@@@@@@@@@")
        emit(Result.Progress())
        val commentDto = CommentRequestDto(
            boardId = boardId, content = content, depth = depth, parentId = parentId, devil = devil
        )
        val result = remoteContentDataSource.sendReply(commentDto)
        result.onSuccess {
            val contentDetailTemp = CommentDetail(
                boardId = boardId,
                content = content,
                depth = 0,
                id = -1,
                likeCount = 0,
                likeIsMe = false,
                ord = 0,
                parentId = null,
                reReplyCount = 0,
                user = CommentUserDto(
                    id = AppUserCache.userInfo?.userId!!,
                    nickname = AppUserCache.userInfo?.nickName!!
                ),
                createdAt = Clock.System.now().toEpochMilliseconds().toString(),
            )
            emit(Result.Success(contentDetailTemp))
        }.onError {
            emit(Result.Error(DataError.Remote.REQUEST_ERROR))
        }
    }

    override suspend fun getContentInfo(
        id: Int
    ): Flow<Result<ContentDetail, DataError.Remote>> = flow {
        // 1. ContentDetail 정보 가져오기
        val contentResult = remoteContentDataSource.getContentDetail(id)
        if (contentResult is Result.Error) {
            emit(Result.Error(contentResult.error))
        }
        val content = (contentResult as Result.Success).data

        // 2. Reply 정보 가져오기
        val replyResult = remoteContentDataSource.getReply(id)

        if (replyResult is Result.Error) {
            emit(Result.Error(replyResult.error))
        }
        println("replyResult : $replyResult")
        val replyInfo = (replyResult as Result.Success).data

        val contentDetail = ContentDetail(
            id = content.id,
            title = content.title,
            content = content.content,
            writer = content.writer,
            isDevil = AppUserCache.isDevil,
            categoryName = content.categoryName,
            rewardPoint = 0,
            tags = "",
            viewCount = content.viewCount,
            likeCount = content.likeCount,
            lastModifiedTime = 0L,
            commentInfo = replyInfo.toCommentInfo(),
            like = content.like,
        )
        emit(Result.Success(contentDetail))
    }

    override suspend fun likeBoardToggle(contentDetail: ContentDetail): Result<ContentDetail, DataError.Remote> {
        remoteContentDataSource.likeBoard(contentDetail.id).onSuccess {
            val contentDetailTemp = contentDetail.copy(
                like = !contentDetail.like,
                likeCount = if (contentDetail.like) contentDetail.likeCount - 1 else contentDetail.likeCount + 1
            )
            return Result.Success(contentDetailTemp)
        }
        return Result.Error(DataError.Remote.REQUEST_ERROR)
    }

    override suspend fun likeCommentToggle(commentDetail: CommentDetail): Result<CommentDetail, DataError.Remote> {
        remoteContentDataSource.likeComment(commentDetail.id).onSuccess {
            val commentDetailTemp = commentDetail.copy(
                likeIsMe = !commentDetail.likeIsMe,
                likeCount = if (commentDetail.likeIsMe) commentDetail.likeCount - 1 else commentDetail.likeCount + 1
            )
            return Result.Success(commentDetailTemp)
        }
        return Result.Error(DataError.Remote.REQUEST_ERROR)
    }
}