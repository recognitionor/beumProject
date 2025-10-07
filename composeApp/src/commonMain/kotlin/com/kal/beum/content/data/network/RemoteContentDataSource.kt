package com.kal.beum.content.data.network

import com.kal.beum.content.data.dto.BoardDetailDto
import com.kal.beum.content.data.dto.CommentDto
import com.kal.beum.content.data.dto.CommentInfoDto
import com.kal.beum.content.data.dto.ContentDetailDto
import com.kal.beum.content.data.dto.ReplyInfoDto
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result

interface RemoteContentDataSource {
    suspend fun getContentDetail(contentId: Int): Result<BoardDetailDto, DataError.Remote>
    suspend fun getReply(contentId: Int): Result<CommentInfoDto, DataError.Remote>
    suspend fun sendReply(commentDto: CommentDto): Result<Boolean, DataError.Remote>
}