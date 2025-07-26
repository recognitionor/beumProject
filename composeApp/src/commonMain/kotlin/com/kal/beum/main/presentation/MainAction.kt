package com.kal.beum.main.presentation

import androidx.compose.runtime.Composable
import com.kal.beum.core.presentation.ToastInfo

sealed interface MainAction {
    data class ToggleDevil(val isDevil: Boolean) : MainAction
    data class PushFullScreen(val screen: (@Composable () -> Unit)) : MainAction
    data object PopFullScreen : MainAction
    data object ClearFullScreen : MainAction
    data class ToastMessage(val toastInfo: ToastInfo? = null) : MainAction
    data object LogOut : MainAction
}