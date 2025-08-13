package com.kal.beum

import androidx.compose.ui.window.ComposeUIViewController
import com.kal.beum.di.initKoin
import kotlinx.cinterop.ExperimentalForeignApi
import com.kal.beum.swiftinterop.KaKaoLoginSDK
import com.kal.beum.swiftinterop.NaverLoginSDK

object ComposeAppBackHandler {
    var onBack: () -> Unit = {}
}

@OptIn(ExperimentalForeignApi::class)
fun MainViewController() = ComposeUIViewController(configure = {
    initKoin()
    KaKaoLoginSDK.initSDK()
    NaverLoginSDK.initSDK()
    ComposeAppBackHandler.onBack = {
        println("back key pressed")
    }
}) { App() }

