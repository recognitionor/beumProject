package com.kal.beum.myinfo.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MyBoardDto(
    val boardType: String,
    val categoryId: Int,
    val categoryName: String,
    val content: String,
    val createTime: String,
    val devil: Boolean,
    val id: Int,
    val like: Boolean,
    val likeCount: Int,
    val replyCount: Int,
    val tags: List<String>,
    val title: String,
    val writer: String
)