package com.kal.beum.level.data

import com.kal.beum.level.data.dto.RankerUserInfoDto
import com.kal.beum.level.domain.RankerUserInfo

fun RankerUserInfoDto.toRankerUserInfo(): RankerUserInfo {
    return RankerUserInfo(
        userId = this.userId,
        nickname = this.nickname,
        profileImageUrl = this.profileImageUrl,
        level = this.level,
        rank = this.rank,
        score = this.score,
        isDevil = this.isDevil
    )
}