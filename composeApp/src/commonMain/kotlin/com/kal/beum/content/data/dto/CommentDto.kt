package com.kal.beum.content.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CommentDto(
    val boardId: Int, val title: String = "", val content: String = "", val depth: Int, val ord: Int
)
