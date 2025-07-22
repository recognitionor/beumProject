package com.kal.beum.myinfo.domain

data class MyContent(
    val id: Int,
    val title: String,
    val content: String,
    val category: String,
    val createdAt: String,
    val likeCount: Int,
    val replyCount: Int
)
