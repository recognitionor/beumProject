package com.kal.beum.community.domain

data class CommunityItem(
    val id: Int,
    val title: String,
    val content: String,
    val writerId: Int,
    val writer: String,
    val categoryName: String,
    val isPopular: Boolean,
    val replyCount: Int,
    val likeCount: Int,
    val lastModifiedTime: String
)
