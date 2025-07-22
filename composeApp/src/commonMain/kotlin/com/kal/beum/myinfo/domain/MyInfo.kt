package com.kal.beum.myinfo.domain

data class MyInfo(
    val id: Int,
    val nickName: String,
    val angelPoint: Int,
    val devilPoint: Int,
    val accessToken: String,
    val refreshToken: String,
    val profileImage: String?,
    val email: String,
    val socialType: String
)
