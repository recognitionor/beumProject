package com.kal.beum.home.presentation

import com.kal.beum.home.domain.HomeData

data class HomeState(
    val homeCommentList: List<HomeData> = emptyList(),
    val isDevilMode: Boolean = false
)