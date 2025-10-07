package com.kal.beum.content.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CommentRequestDto(
    val boardId: Int,
    val content: String = "",
    val depth: Int,
    val parentId: Int,
    val devil: Boolean
)
