package com.kal.beum.community.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GetBoardsRequestDto(
    val page: Int, val size: Int, val isDevil: Boolean, val categoryId: Int
)
