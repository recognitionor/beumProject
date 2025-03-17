package com.kal.beum.home.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class HomeCommentDto(
    val id: Int = -1, val comment: String = ""
)
