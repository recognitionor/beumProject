package com.kal.beum.level.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SortedPointUserDto(
    val nickname: String,
    val point: Int,
    val userId: Int
)