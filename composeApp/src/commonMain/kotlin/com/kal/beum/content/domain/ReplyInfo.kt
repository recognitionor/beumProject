package com.kal.beum.content.domain

data class ReplyInfo(
    val writer: String,
    val content: String,
    val likeCount: Int,
    val isSelected: Boolean = false,
    val replyList: List<ReplyInfo> = emptyList(),
    val lastModifiedTime: Long
)
