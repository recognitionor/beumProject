package com.kal.beum.content.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CommentInfoDto(
    val boardId: Int,
    val commentCount: Int,
    val comments: List<String> = emptyList(),
)
