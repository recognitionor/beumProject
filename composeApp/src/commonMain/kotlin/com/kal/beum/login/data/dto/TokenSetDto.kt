package com.kal.beum.login.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class TokenSetDto(
    var accessToken: String, var refreshToken: String
)