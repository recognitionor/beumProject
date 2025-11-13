package com.kal.beum.login.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val userId: String = "",
    @SerialName("nickname")
    val nickName: String = "",
    val socialType: Int = 0,
    val email: String = "",
    val tokenSet: TokenSetDto? = null,
    val profileImageId: String = "",
    val needSignUp: Boolean = false,
    val angelPoint: Int = 0,
    val devilPoint: Int = 0

)