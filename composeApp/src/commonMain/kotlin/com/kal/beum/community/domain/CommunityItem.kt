package com.kal.beum.community.domain

data class CommunityItem(
    val id: Int,
    val title: String,
    val content: String,
    val writer: String,
    val categoryName: String,
    val isPopular: Boolean
)
