package com.kal.beum.content.domain

data class ContentDetail(
    val id: Int,
    val title: String,
    val content: String,
    val writer: String,
    val isDevil: Boolean,
    val categoryName: String,
    val rewardPoint: Int = 0,
    val tags: String,
    val likeCount: Int = 0,
    val viewCount: Int = 0,
    val lastModifiedTime: Long,
    val replyList: List<ReplyInfo> = emptyList()
)
