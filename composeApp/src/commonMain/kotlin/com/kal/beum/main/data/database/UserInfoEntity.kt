package com.kal.beum.main.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserInfoEntity(
    @PrimaryKey(autoGenerate = false) val id: Int = 1,
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
