package com.kal.beum.content.data.network

import com.kal.beum.content.data.dto.BoardDetailDto
import com.kal.beum.content.data.dto.CommentDetailDto
import com.kal.beum.content.data.dto.CommentInfoDto
import com.kal.beum.content.data.dto.CommentRequestDto
import com.kal.beum.content.data.dto.ReportRequestDto
import com.kal.beum.core.data.ApiConstants
import com.kal.beum.core.data.ApiConstants.KEY.KEY_BOARD_ID
import com.kal.beum.core.data.ApiConstants.KEY.KEY_TARGET_USER_ID
import com.kal.beum.core.data.AppUserCache
import com.kal.beum.core.data.safeCall
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.core.domain.Result.Error
import com.kal.beum.core.domain.Result.Progress
import com.kal.beum.core.domain.Result.Success
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
        println("response.bodyAsText() : ${response.bodyAsText()}")
        if (response.status.value == 200) {
            val rest = response.body<BoardDetailDto>()
            return Success(rest)
        } else {
            return Error(DataError.Remote.REQUEST_ERROR)
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
                    parameters.append(ApiConstants.KEY.KEY_CATEGORY_NAME, "test")
                }
            }
        }
        println("getReply : ${response.bodyAsText()}" )
        if (response.status.value == 200) {
            val replyInfo = response.body<CommentInfoDto>()
            println("getReply replyInfo : $replyInfo" )
            return Success(replyInfo)
        } else {
            return Error(DataError.Remote.REQUEST_ERROR)
        }
    }

    override suspend fun sendReply(commentDto: CommentRequestDto): Result<CommentDetailDto, DataError.Remote> {
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
        return if (response.status.value == 200) {
            Success(response.body<CommentDetailDto>())
        } else {
            Error(DataError.Remote.FAILED_BOARD)
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
            Success(true)
        } else {
            Error(DataError.Remote.FAILED_BOARD)
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
            Success(true)
        } else {
            Error(DataError.Remote.FAILED_BOARD)
        }
    }

    override suspend fun reportContent(reportRequestDto: ReportRequestDto): Result<Boolean, DataError.Remote> {
        val response = httpClient.post(ApiConstants.Endpoints.REPORT) {
            headers {
                AppUserCache.userInfo?.accessToken?.let {
                    append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                }
            }
            setBody(
                reportRequestDto
            )
        }
        println("reportContent response ${response.bodyAsText()}")
        return if (response.status.value == 200) {
            Success(true)
        } else {
            Error(DataError.Remote.REQUEST_ERROR)
        }
    }

    override suspend fun reportUser(reportRequestDto: ReportRequestDto): Result<Boolean, DataError.Remote> {
        val response = httpClient.post(ApiConstants.Endpoints.REPORT) {
            headers {
                AppUserCache.userInfo?.accessToken?.let {
                    append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                }
            }
            setBody(
                reportRequestDto
            )
        }
        println("reportContent response ${response.bodyAsText()}")
        return if (response.status.value == 200) {
            Success(true)
        } else {
            Error(DataError.Remote.REQUEST_ERROR)
        }
    }

    override suspend fun pickComment(
        targetUserId: String, boardId: String
    ): Result<Boolean, DataError.Remote> {
        println("ktor : pickComment")
        val result = safeCall<String> {
            httpClient.post(ApiConstants.Endpoints.POINT) {
                headers {
                    AppUserCache.userInfo?.accessToken?.let {
                        append(ApiConstants.KEY.KEY_AUTH_TOKEN, it)
                    }
                }
                url {
                    parameters.append(KEY_BOARD_ID, boardId)
                    parameters.append(KEY_TARGET_USER_ID, targetUserId)
                }
            }
        }

        println("~~~~~~~~~~~~ : $result")

        return when (result) {
            is Success -> Success(true)
            is Error -> Error(result.error)
            is Progress -> Progress()
        }
    }
}
