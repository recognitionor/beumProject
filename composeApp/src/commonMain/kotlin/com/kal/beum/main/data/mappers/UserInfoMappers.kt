package com.kal.beum.main.data.mappers

import com.kal.beum.main.data.database.UserInfoEntity
import com.kal.beum.main.domain.UserInfo
import com.kal.beum.login.data.dto.LoginResponseDto

fun UserInfoEntity.toUserInfo(): UserInfo {
    return UserInfo(
        userId = this.userId,
        nickName = this.nickName,
        socialType = this.socialType,
        email = this.email,
        sessionKey = this.sessionKey,
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        profileImageId = this.profileImageId,
        needSignUp = this.needSignUp
    )
}

fun UserInfo.toUserInfoEntity(): UserInfoEntity {
    return UserInfoEntity(
        userId = this.userId,
        nickName = this.nickName,
        socialType = this.socialType,
        email = this.email,
        sessionKey = this.sessionKey,
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        profileImageId = this.profileImageId,
        needSignUp = this.needSignUp
    )
}

private fun LoginResponseDto.toDomain() = UserInfo(
    userId = this.userId,
    nickName = this.nickName,
    socialType = this.socialType,
    email = this.email,
    sessionKey = "",
    accessToken = this.tokenSet?.accessToken?:"",
    refreshToken = this.tokenSet?.refreshToken?:"",
    profileImageId = this.profileImageId,
    needSignUp = this.needSignUp
)