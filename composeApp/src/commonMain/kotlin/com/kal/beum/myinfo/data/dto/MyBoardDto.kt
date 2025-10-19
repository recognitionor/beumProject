package com.kal.beum.myinfo.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MyBoardDto(
    val categoryId: Int,
    val categoryName: String,
    val content: String,
    val createTime: String,
    val devil: Boolean,
    val id: Int,
    val isDevil: Boolean,
    val like: Boolean,
    val likeCount: Int,
    val replyCount: Int,
    val tags: List<String> = emptyList(),
    val title: String,
    val writer: String
)