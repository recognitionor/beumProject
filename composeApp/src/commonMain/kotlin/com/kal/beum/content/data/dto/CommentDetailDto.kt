package com.kal.beum.content.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CommentDetailDto(
    val boardId: Int,
    val content: String,
    val depth: Int,
    val id: Int,
    val likeCount: Int,
    val likeIsMe: Boolean,
    val ord: Int,
    val parentId: Int? = null,
    val reReplyCount: Int,
    val user: CommentUserDto,
    val createdAt: String
)
