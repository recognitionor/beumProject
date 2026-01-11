package com.kal.beum.main.data.mappers

import com.kal.beum.main.data.database.UserInfoEntity
import com.kal.beum.main.domain.UserInfo
import com.kal.beum.login.data.dto.LoginResponseDto
import com.kal.beum.main.domain.SocialType

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
        needSignUp = this.needSignUp,
        angelPoint = this.angelPoint,
        devilPoint = this.devilPoint
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
        needSignUp = this.needSignUp,
        angelPoint = this.angelPoint,
        devilPoint = this.devilPoint
    )
}

private fun LoginResponseDto.toDomain() = UserInfo(
    userId = this.userId.toString(),
    nickName = this.nickName ?: "",
    socialType = SocialType.toTypeCode(this.socialType),
    email = this.email,
    sessionKey = "",
    accessToken = this.tokenSet?.accessToken ?: "",
    refreshToken = this.tokenSet?.refreshToken ?: "",
    profileImageId = this.profileImageId.toString(),
    needSignUp = this.needSignUp,
    angelPoint = this.angelPoint,
    devilPoint = this.devilPoint
)