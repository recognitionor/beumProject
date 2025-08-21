package com.kal.beum.main.presentation

import androidx.compose.runtime.Composable
import com.kal.beum.core.presentation.ToastInfo
import com.kal.beum.main.domain.UserInfo

data class MainState(
    val isOnboardingDone: Boolean = false,
    val isSplashDone: Boolean = false,
    val userInfo: UserInfo? = null,
    val isDevil: Boolean = false,
    val showToast: ToastInfo? = null,
    val isProgress: Boolean = false,
    val fullScreen: List<@Composable() (() -> Unit)?> = emptyList(),
    val isFullScreen: Boolean = false
)