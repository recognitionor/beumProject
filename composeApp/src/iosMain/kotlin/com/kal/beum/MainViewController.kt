package com.kal.beum

import androidx.compose.ui.window.ComposeUIViewController
import com.kal.beum.di.initKoin

fun MainViewController() = ComposeUIViewController(configure = {
    initKoin()
}) { App() }