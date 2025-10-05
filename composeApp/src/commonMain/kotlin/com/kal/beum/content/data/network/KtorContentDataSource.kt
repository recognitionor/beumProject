package com.kal.beum.content.data.network

import com.kal.beum.content.data.dto.CommentDto
import com.kal.beum.content.data.dto.ContentDetailDto
import com.kal.beum.content.data.dto.ReplyInfoDto
import com.kal.beum.core.data.ApiConstants
import com.kal.beum.core.data.AuthTokenCache
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class KtorContentDataSource(private val httpClient: HttpClient) : RemoteContentDataSource {
    val reReplyList = listOf(
        ReplyInfoDto(
            writer = "유저1",
            content = "첫 번째 대댓글 댓글입니다.",
            isLiked = 1,
            isSelected = false,
            replyList = emptyList(),
            lastModifiedTime = 1717245296000L
        ), ReplyInfoDto(
            writer = "유저2",
            content = "두 번째 대댓글 댓글입니다.",
            isLiked = 0,
            isSelected = false,
            replyList = emptyList(),
            lastModifiedTime = 1717245396000L
        ), ReplyInfoDto(
            writer = "유저3",
            content = "세 번째 대댓글 댓글입니다.",
            isLiked = 1,
            isSelected = true,
            replyList = emptyList(),
            lastModifiedTime = 1717245496000L
        ), ReplyInfoDto(
            writer = "유저4",
            content = "네 번째 대댓글 댓글입니다.",
            isLiked = 0,
            isSelected = false,
            replyList = emptyList(),
            lastModifiedTime = 1717245596000L
        ), ReplyInfoDto(
            writer = "유저5",
            content = "다섯 번째 대댓글 댓글입니다.",
            isLiked = 1,
            isSelected = false,
            replyList = emptyList(),
            lastModifiedTime = 1717245696000L
        ), ReplyInfoDto(
            writer = "유저6",
            content = "여섯 번째 댓글입니다.",
            isLiked = 0,
            isSelected = false,
            replyList = emptyList(),
            lastModifiedTime = 1717245796000L
        )
    )
    val replyList = listOf(
        ReplyInfoDto(
            writer = "유저1",
            content = "첫 번째 댓글입니다.",
            isLiked = 1,
            isSelected = false,
            replyList = reReplyList,
            lastModifiedTime = 1717245296000L
        ), ReplyInfoDto(
            writer = "유저2",
            content = "두 번째 댓글입니다.",
            isLiked = 0,
            isSelected = false,
            replyList = reReplyList,
            lastModifiedTime = 1717245396000L
        ), ReplyInfoDto(
            writer = "유저3",
            content = "세 번째 댓글입니다.",
            isLiked = 1,
            isSelected = true,
            replyList = reReplyList,
            lastModifiedTime = 1717245496000L
        ), ReplyInfoDto(
            writer = "유저4",
            content = "네 번째 댓글입니다.",
            isLiked = 0,
            isSelected = false,
            replyList = reReplyList,
            lastModifiedTime = 1717245596000L
        ), ReplyInfoDto(
            writer = "유저5",
            content = "다섯 번째 댓글입니다.",
            isLiked = 1,
            isSelected = false,
            replyList = reReplyList,
            lastModifiedTime = 1717245696000L
        ), ReplyInfoDto(
            writer = "유저6",
            content = "여섯 번째 댓글입니다.",
            isLiked = 0,
            isSelected = false,
            replyList = reReplyList,
            lastModifiedTime = 1717245796000L
        )
    )

    override suspend fun getContentDetail(contentId: Int): Result<ContentDetailDto, DataError.Remote> {
        val response = httpClient.get(ApiConstants.Endpoints.BOARD) {
            headers {
                AuthTokenCache.accessToken?.let {
                    append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                }
            }
        }
        println("getContentDetail response: ${response.body<String>()}")

        return Result.Success(
            ContentDetailDto(
                id = 1,
                title = "목업 제목",
                content = "이것은 목업 본문입니다.",
                writer = "홍길동",
                isDevil = false,
                categoryName = "자유게시판",
                rewardPoint = 100,
                tags = "#KMP #Compose",
                likeCount = 10,
                viewCount = 200,
                lastModifiedTime = 1717245296000L,
                replyList = replyList
            )
        )
    }

    override suspend fun sendReply(commentDto: CommentDto): Result<Boolean, DataError.Remote> {
        println("sendReply")
        val response = httpClient.post(ApiConstants.Endpoints.COMMENT) {
            headers {
                AuthTokenCache.accessToken?.let {
                    append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                }
            }
            setBody(
                commentDto
            )
        }
        return if (response.status.value == 200) {
            Result.Success(true)
        } else {
            Result.Error(DataError.Remote.FAILED_BOARD)
        }
    }
}
