@file:OptIn(ExperimentalForeignApi::class)

package com.kal.beum.login.data.client

import com.kal.beum.core.domain.DataError
import com.kal.beum.login.domain.LoginClient
import com.kal.beum.login.domain.SocialToken
import com.kal.beum.swiftinterop.AppleLoginSDK
import kotlinx.cinterop.ExperimentalForeignApi

actual class AppleLoginClient actual constructor(obj: Any?) : LoginClient {
    
    actual override fun login(
        type: Int,
        callback: (SocialToken?, DataError.Remote?) -> Unit
    ) {
        AppleLoginSDK.requestLoginWithCompletion { identityToken, authorizationCode, error ->
            if (error != null) {
                println("AppleLoginClient login failed: ${error.localizedDescription}")
                callback.invoke(null, DataError.Remote.LOGIN_FAILED)
            } else {
                if (identityToken == null || identityToken.toString().isEmpty()) {
                    callback.invoke(null, DataError.Remote.LOGIN_FAILED)
                } else {
                    // identityToken을 accessToken으로, authorizationCode를 refreshToken으로 전달
                    val authCode = authorizationCode?.toString() ?: ""
                    println("AppleLoginClient login success - identityToken: ${identityToken.toString().take(50)}...")
                    callback.invoke(SocialToken(identityToken.toString(), authCode), null)
                }
            }
        }
    }

    actual override fun logout() {
        AppleLoginSDK.logoutWithCompletion { error ->
            if (error != null) {
                println("AppleLoginClient logout failed: ${error.localizedDescription}")
            } else {
                println("AppleLoginClient logout success")
            }
        }
    }
}