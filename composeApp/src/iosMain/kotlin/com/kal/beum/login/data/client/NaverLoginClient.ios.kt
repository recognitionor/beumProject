@file:OptIn(ExperimentalForeignApi::class)

package com.kal.beum.login.data.client

import androidx.navigation.compose.NavHost
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.login.domain.LoginClient
import com.kal.beum.login.domain.SocialToken
import com.kal.beum.swiftinterop.NaverLoginSDK
import kotlinx.cinterop.ExperimentalForeignApi

actual class NaverLoginClient actual constructor(obj: Any?) : LoginClient {

    actual override fun login(
        type: Int,
        callback: (SocialToken?, DataError.Remote?) -> Unit
    ) {
        NaverLoginSDK.requestLoginWithCompletion { accessToken, refreshToken, error ->
            if (accessToken != null && accessToken.isNotEmpty() && refreshToken != null && refreshToken.isNotEmpty()) {
                callback(SocialToken(accessToken, refreshToken), null)
            } else {
                callback(null, DataError.Remote.LOGIN_FAILED)
            }
        }
    }

    actual override fun logout() {
        NaverLoginSDK.logoutWithCompletion {
            if (it != null) {
                println("NaverLoginSDK.logoutWithCompletion error: ${it.localizedDescription}")
            } else {
                println("NaverLoginSDK.logoutWithCompletion success")
            }
        }
    }
}
