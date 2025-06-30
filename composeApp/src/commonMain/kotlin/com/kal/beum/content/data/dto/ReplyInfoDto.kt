package com.kal.beum.content.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReplyInfoDto(
    val writer: String = "",
    val content: String = "",
    val isLiked: Int,
    val isSelected: Boolean = false,
    val replyList: List<ReplyInfoDto> = emptyList(),
    val lastModifiedTime: Long
)
