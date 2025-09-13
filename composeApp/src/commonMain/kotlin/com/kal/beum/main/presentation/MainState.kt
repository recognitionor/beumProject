package com.kal.beum.main.presentation

import androidx.compose.runtime.Composable
import com.kal.beum.core.presentation.ToastInfo
import com.kal.beum.main.domain.UserInfo
import com.kal.beum.myinfo.domain.MyInfo
import com.kal.beum.write.domain.WritingCategory

sealed class FullScreenType {
    data class MyInfoDetailScreen(val info: MyInfo) : FullScreenType()
    data class SettingsScreen(val info: MyInfo) : FullScreenType()
    data class ContentDetailScreen(val id: Int) : FullScreenType()
    data class DraftDialog(
        val onNewClick: () -> Unit,
        val onContinueClick: () -> Unit,
        val onDismiss: (selectItem: WritingCategory?) -> Unit
    ) : FullScreenType()

    object WritingScreen : FullScreenType()

    object NoticeScreen : FullScreenType()

    object PrivacyPolicyScreen : FullScreenType()

    object TermScreen : FullScreenType()

    object ServicePolicyInfoScreen : FullScreenType()

    data class ReportConfirmDialog(val onDismiss: () -> Unit, val onContinueClick: () -> Unit) : FullScreenType()

    data class LogOutDialog(val onDismiss: () -> Unit, val logoutClick: () -> Unit) : FullScreenType()
}

data class MainState(
    val isOnboardingDone: Boolean = false,
    val isSplashDone: Boolean = false,
    val userInfo: UserInfo? = null,
    val isDevil: Boolean = false,
    val showToast: ToastInfo? = null,
    val isProgress: Boolean = false,
    val fullScreenStack: List<FullScreenType> = emptyList(),
    val fullScreen: List<@Composable() (() -> Unit)?> = emptyList(),
    val isFullScreen: Boolean = false
)