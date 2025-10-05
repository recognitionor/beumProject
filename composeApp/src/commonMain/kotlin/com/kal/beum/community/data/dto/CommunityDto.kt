package com.kal.beum.community.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CommunityDto(
    val page: Int, val size: Int, val boardList: List<CommunityCommentItemDto> = emptyList()
)
