package com.kal.beum.myinfo.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MyContentDto(
    val id: Int,
    val title: String,
    val content: String,
    val category: String,
    val createdAt: String,
    val likeCount: Int,
    val replyCount: Int
)