package com.kal.beum.main.presentation

import androidx.compose.runtime.Composable
import com.kal.beum.core.presentation.ToastInfo

sealed interface MainAction {
    data class ToggleDevil(val isDevil: Boolean) : MainAction
    data class SetFullScreen(val screen: (@Composable () -> Unit)?) : MainAction
    data class ToastMessage(val toastInfo: ToastInfo? = null) : MainAction
}