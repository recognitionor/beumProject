package com.kal.beum.core.presentation

import androidx.compose.runtime.Composable

@Composable
actual fun CommonBackHandler(onBack: () -> Unit) {
    // iOS handles back via swipe gestures or native navigation controllers
}
