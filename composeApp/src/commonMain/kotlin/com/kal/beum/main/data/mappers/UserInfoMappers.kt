package com.kal.beum.main.data.mappers

import com.kal.beum.main.data.database.UserInfoEntity
import com.kal.beum.main.domain.UserInfo

fun UserInfoEntity.toUserInfo(): UserInfo {
    return UserInfo(this.userId, this.nickName, this.socialType, this.mail, this.sessionKey)
}

fun UserInfo.toUserInfoEntity(): UserInfoEntity {
    return UserInfoEntity(
        userId = this.id,
        nickName = this.nickName,
        socialType = this.socialType,
        mail = this.mail,
        sessionKey = this.sessionKey
    )
}