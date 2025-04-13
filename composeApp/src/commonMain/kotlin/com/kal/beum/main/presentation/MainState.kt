package com.kal.beum.main.presentation

import com.kal.beum.main.domain.UserInfo

data class MainState(
    val isOnboardingDone: Boolean = false,
    val isSplashDone: Boolean = false,
    val userInfo: UserInfo? = null,
    val isDevil: Boolean = false,
)