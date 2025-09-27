package com.kal.beum.main.presentation

import com.kal.beum.community.presentation.CommunityAction
import com.kal.beum.core.presentation.ToastInfo

sealed interface MainAction {
    data class ToggleDevil(val isDevil: Boolean) : MainAction
    data class PushFullScreen(val fullScreen: FullScreenType) : MainAction

    data object PopFullScreen : MainAction
    data object ClearFullScreen : MainAction
    data class ToastMessage(val toastInfo: ToastInfo? = null) : MainAction
    data object LogOut : MainAction

    object GetTempWriting : MainAction

    object OnDraftDialog : MainAction
}