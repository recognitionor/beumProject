package com.kal.beum.main.domain

data class UserInfo(
    val userId: String,
    val nickName: String,
    val socialType: Int,
    val email: String,
    val sessionKey: String,
    val accessToken: String,
    val refreshToken: String,
    val profileImageId: String,
    val needSignUp: Boolean
)
