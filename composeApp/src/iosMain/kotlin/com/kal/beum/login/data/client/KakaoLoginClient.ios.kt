@file:OptIn(ExperimentalForeignApi::class)

package com.kal.beum.login.data.client

import com.kal.beum.core.domain.DataError
import com.kal.beum.login.domain.LoginClient
import com.kal.beum.login.domain.SocialToken
import com.kal.beum.swiftinterop.KaKaoLoginSDK
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.compose.scope.KoinScope

actual class KaKaoLoginClient actual constructor(obj: Any?) : LoginClient {
    actual override fun login(
        type: Int, callback: (SocialToken?, DataError.Remote?) -> Unit
    ) {
        KaKaoLoginSDK.requestLoginWithCompletion { accessToken, refreshToken, error ->
            if (error != null) {
                callback.invoke(null, DataError.Remote.LOGIN_FAILED)
            } else {
                if (accessToken == null || accessToken.isEmpty() || refreshToken == null || refreshToken.isEmpty()) {
                    callback.invoke(null, DataError.Remote.LOGIN_FAILED)
                } else {
                    callback.invoke(SocialToken(accessToken, refreshToken), null)
                }
            }
        }
    }


    actual override fun logout() {
        KaKaoLoginSDK.logoutWithCompletion { error ->

        }
    }
}
