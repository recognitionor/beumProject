package com.kal.beum.login.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val userId: Int = 0,
    @SerialName("nickName")
    val nickName: String? = null,
    val socialType: String = "",
    val email: String = "",
    val tokenSet: TokenSetDto? = null,
    val profileImageId: Int = 0,
    val needSignUp: Boolean = false,
    val angelPoint: Int = 0,
    val devilPoint: Int = 0

)