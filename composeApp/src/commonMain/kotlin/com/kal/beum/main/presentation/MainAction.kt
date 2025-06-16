package com.kal.beum.main.presentation

import androidx.compose.runtime.Composable

sealed interface MainAction {
    data class ToggleDevil(val isDevil: Boolean) : MainAction
    data class SetFullScreen(val screen: (@Composable () -> Unit)?) : MainAction
}