package com.kal.beum.main.presentation

import androidx.compose.ui.graphics.Color
import com.kal.beum.community.presentation.CommunityAction
import com.kal.beum.core.presentation.ToastInfo

sealed interface MainAction {
    data class ToggleDevil(val isDevil: Boolean) : MainAction

    data class SurfaceColor(val surfaceColor: Color) : MainAction
    data class PushFullScreen(val fullScreen: FullScreenType) : MainAction

    data class CloseFullScreen(val fullScreen: FullScreenType) : MainAction

    data object PopFullScreen : MainAction
    data object ClearFullScreen : MainAction
    data class ToastMessage(val toastInfo: ToastInfo? = null) : MainAction
    data object LogOut : MainAction

    data object Withdraw : MainAction
    object GetTempWriting : MainAction

    data class NewWriting(val callback: () -> Unit) : MainAction

    object OnDraftDialog : MainAction
}