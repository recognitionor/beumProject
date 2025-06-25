package com.kal.beum.community.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CommunityItemDto(
    val id: Int,
    val title: String,
    val content: String,
    val writer: String,
    val categoryName: String,
    val isPopular: Boolean,
    val lastModifiedTime: Long
)
