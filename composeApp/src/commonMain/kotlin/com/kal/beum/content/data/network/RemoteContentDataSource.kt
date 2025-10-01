package com.kal.beum.content.data.network

import com.kal.beum.content.data.dto.CommentDto
import com.kal.beum.content.data.dto.ContentDetailDto
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result

interface RemoteContentDataSource {
    suspend fun getContentDetail(contentId: Int): Result<ContentDetailDto, DataError.Remote>
    suspend fun sendReply(commentDto: CommentDto): Result<Boolean, DataError.Remote>
}