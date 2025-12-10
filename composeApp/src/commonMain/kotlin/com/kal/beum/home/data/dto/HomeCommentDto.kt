package com.kal.beum.home.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class HomeCommentDto(
    val boardId: Int = -1, val commentId: Int = -1, val content: String = ""
)
