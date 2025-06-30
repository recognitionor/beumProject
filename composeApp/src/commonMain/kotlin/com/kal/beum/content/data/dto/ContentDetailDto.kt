package com.kal.beum.content.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ContentDetailDto(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val writer: String = "",
    val isDevil: Boolean = false,
    val categoryName: String = "",
    val rewardPoint: Int = 0,
    val tags: String = "",
    val likeCount: Int = 0,
    val viewCount: Int = 0,
    val lastModifiedTime: Long,
    val replyList: List<ReplyInfoDto> = emptyList()
)
