package com.kal.beum.community.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CommunityCommentItemDto(
    val id: Int,
    val title: String,
    val writer: String = "",
    val content: String,
    val categoryId: Int,
    val categoryName: String,
    val tags: List<String>,
    val replyCount: Int,
    val likeCount: Int,
    val createTime: String,
    val isDevil: Boolean,
    val like: Boolean,
)
