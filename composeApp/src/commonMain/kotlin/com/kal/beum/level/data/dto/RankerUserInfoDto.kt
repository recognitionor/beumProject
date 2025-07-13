package com.kal.beum.level.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RankerUserInfoDto(
    val userId: String,
    val nickname: String,
    val profileImageUrl: String,
    val level: Int,
    val rank: Int,
    val score: Int,
    val isDevil: Boolean
)