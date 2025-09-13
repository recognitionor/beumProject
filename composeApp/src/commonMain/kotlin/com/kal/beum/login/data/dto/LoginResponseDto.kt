package com.kal.beum.login.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val userId: String = "",
    val nickName: String = "",
    val socialType: Int = 0,
    val email: String = "",
    val tokenSet: TokenSetDto? = null,
    val profileImageId: String = "",
    val needSignUp: Boolean = false
)