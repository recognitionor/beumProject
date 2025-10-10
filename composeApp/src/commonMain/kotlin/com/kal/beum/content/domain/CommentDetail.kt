package com.kal.beum.content.domain

import com.kal.beum.content.data.dto.CommentUserDto

data class CommentDetail(
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