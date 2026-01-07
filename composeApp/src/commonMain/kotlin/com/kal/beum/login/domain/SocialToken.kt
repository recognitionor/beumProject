package com.kal.beum.login.domain

data class SocialToken(
    val accessToken: String,
    val refreshToken: String,
    val email: String? = null,
    val name: String? = null
)