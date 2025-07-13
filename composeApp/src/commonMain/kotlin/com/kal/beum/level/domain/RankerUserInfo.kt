package com.kal.beum.level.domain

data class RankerUserInfo(
    val userId: String,
    val nickname: String,
    val profileImageUrl: String,
    val level: Int,
    val rank: Int,
    val score: Int,
    val isDevil: Boolean
)
