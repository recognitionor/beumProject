package com.kal.beum.level.presentaion

import com.kal.beum.level.domain.RankerUserInfo

data class RankingState(
    val rankerUserList: List<RankerUserInfo> = emptyList(),
)