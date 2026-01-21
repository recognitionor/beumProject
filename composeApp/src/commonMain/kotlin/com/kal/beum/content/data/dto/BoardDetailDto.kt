package com.kal.beum.content.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BoardDetailDto(
    val boardType: Boolean = false,
    val categoryId: Int = -1,
    val categoryName: String = "",
    val content: String = "",
    val createTime: String = "",
    val id: Int = -1,
    val like: Boolean = false,
    val likeCount: Int = 0,
    val tags: List<String> = emptyList(),
    val selectingStatus: Int = 0,
    val title: String = "",
    val viewCount: Int = 0,
    val writerId: Int = 0,
    val writer: String = ""
)