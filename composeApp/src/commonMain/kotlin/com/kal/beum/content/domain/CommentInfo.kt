package com.kal.beum.content.domain

data class CommentInfo(
    val boardId: Int,
    val commentCount: Int,
    val comments: List<String> = emptyList(),
)