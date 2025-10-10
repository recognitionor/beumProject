package com.kal.beum.content.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class CommentUserDto(
    val id: String,
    val nickname: String = "",
)
