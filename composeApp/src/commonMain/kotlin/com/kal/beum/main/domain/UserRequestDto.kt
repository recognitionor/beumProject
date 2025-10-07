package com.kal.beum.main.domain

import kotlinx.serialization.Serializable

@Serializable
data class UserRequestDto(
    val userId: String,
    val nickName: String,
)
