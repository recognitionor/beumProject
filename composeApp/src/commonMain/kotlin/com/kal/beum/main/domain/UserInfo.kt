package com.kal.beum.main.domain

data class UserInfo(
    val id: String,
    val nickName: String,
    val socialType: Int,
    val mail: String,
    val sessionKey: String
)
