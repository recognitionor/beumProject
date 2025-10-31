package com.kal.beum.content.data.network

import com.kal.beum.content.data.dto.BoardDetailDto
import com.kal.beum.content.data.dto.CommentInfoDto
import com.kal.beum.content.data.dto.CommentRequestDto
import com.kal.beum.core.data.ApiConstants
import com.kal.beum.core.data.AppUserCache
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText

class KtorContentDataSource(private val httpClient: HttpClient) : RemoteContentDataSource {

    override suspend fun getContentDetail(contentId: Int): Result<BoardDetailDto, DataError.Remote> {
        val response = httpClient.get(ApiConstants.Endpoints.BOARD + "/$contentId") {
            headers {
                AppUserCache.userInfo?.accessToken?.let {
                    append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                }
            }
        }
        if (response.status.value == 200) {
            val rest = response.body<BoardDetailDto>()
            return Result.Success(rest)
        } else {
            return Result.Error(DataError.Remote.REQUEST_ERROR)
        }
    }

    override suspend fun getReply(
        boardId: Int,
        commentId: Int?
    ): Result<CommentInfoDto, DataError.Remote> {
        println("getReply boardId : $boardId" )
        println("getReply commentId : $commentId" )
        val response = httpClient.get(ApiConstants.Endpoints.COMMENTS + "/$boardId") {
            headers {
                AppUserCache.userInfo?.accessToken?.let {
                    append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                }
            }
            if (commentId != null) {
                url {
                    parameters.append(ApiConstants.KEY.KEY_PARENT_ID, commentId.toString())
                }
            }
        }
        println("getReply : $response" )
        if (response.status.value == 200) {
            val replyInfo = response.body<CommentInfoDto>()
            println("getReply replyInfo : $replyInfo" )
            return Result.Success(replyInfo)
        } else {
            return Result.Error(DataError.Remote.REQUEST_ERROR)
        }
    }

    override suspend fun sendReply(commentDto: CommentRequestDto): Result<Boolean, DataError.Remote> {
        val response = httpClient.post(ApiConstants.Endpoints.COMMENT) {
            headers {
                AppUserCache.userInfo?.accessToken?.let {
                    append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                }
            }
            setBody(
                commentDto
            )
        }
        println("response.body() #####: $response")
        println("response.body() !!!!!!!!: ${response.bodyAsText()}")
        return if (response.status.value == 200) {
            Result.Success(true)
        } else {
            Result.Error(DataError.Remote.FAILED_BOARD)
        }
    }

    override suspend fun likeBoard(boardId: Int): Result<Boolean, DataError.Remote> {
        val response = httpClient.post(ApiConstants.Endpoints.LIKE_BOARD + "/$boardId") {
            headers {
                AppUserCache.userInfo?.accessToken?.let {
                    append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                }
            }
        }
        println("likeBoard response ${response.bodyAsText()}")
        return if (response.status.value == 200) {
            Result.Success(true)
        } else {
            Result.Error(DataError.Remote.FAILED_BOARD)
        }
    }

    override suspend fun likeComment(replyId: Int): Result<Boolean, DataError.Remote> {
        val response = httpClient.post(ApiConstants.Endpoints.LIKE_REPLY + "/$replyId") {
            headers {
                AppUserCache.userInfo?.accessToken?.let {
                    append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                }
            }
        }
        println("likeComment response ${response.bodyAsText()}")
        return if (response.status.value == 200) {
            Result.Success(true)
        } else {
            Result.Error(DataError.Remote.FAILED_BOARD)
        }
    }
}
