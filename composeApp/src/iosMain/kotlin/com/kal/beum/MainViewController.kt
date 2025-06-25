package com.kal.beum

import androidx.compose.ui.window.ComposeUIViewController
import com.kal.beum.di.initKoin

object ComposeAppBackHandler {
    var onBack: () -> Unit = {}
}

fun MainViewController() = ComposeUIViewController(configure = {
    initKoin()
    ComposeAppBackHandler.onBack = {
        println("back key pressed")
    }
}) { App() }

