package com.kal.beum.main.data.mappers

import com.kal.beum.main.data.database.UserInfoEntity
import com.kal.beum.main.domain.UserInfo

fun UserInfoEntity.toUserInfo(): UserInfo {
    return UserInfo(this.userId, this.nickName, this.socialType, this.mail, this.sessionKey)
}