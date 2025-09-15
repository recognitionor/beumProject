package com.kal.beum.write.domain

import kotlinx.serialization.Serializable

@Serializable
data class BoardSubmitRequest(
    val boardReq: WritingInfoRequest,
    val userDto: WritingUserDto
)

@Serializable
data class WritingUserDto(
    val userId: Int, val nickname: String
)

@Serializable
data class WritingInfoRequest(
    val title: String,
    val content: String,
    val categoryId: Int,
    val rewardPoint: Int,
    val tags: List<String>,
    val devil: Boolean
)
