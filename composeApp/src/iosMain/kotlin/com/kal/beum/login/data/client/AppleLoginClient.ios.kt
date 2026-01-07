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
        // Swift에서 5개의 인자를 던져주므로, 람다 파라미터를 5개로 맞춰줍니다.
        AppleLoginSDK.requestLoginWithCompletion { identityToken, authorizationCode, email, fullName, error ->
            if (error != null) {
                println("AppleLoginClient login failed: ${error.localizedDescription}")
                callback.invoke(null, DataError.Remote.LOGIN_FAILED)
            } else {
                // AppleLoginSDK(Swift)에서 Firebase 로그인을 거쳐 반환된 값들입니다.
                // identityToken -> Firebase ID Token
                // authorizationCode -> Firebase UID
                val firebaseIdToken = identityToken ?: ""

                if (firebaseIdToken.isEmpty()) {
                    // 토큰이 없으면 로그인 실패 처리
                    callback.invoke(null, DataError.Remote.LOGIN_FAILED)
                } else {
                    val firebaseUid = authorizationCode ?: ""

                    // ⭐ Swift에서 직접 추출한 이메일과 이름을 가져옵니다.
                    val userEmail = email ?: ""
                    val userName = fullName ?: ""

                    println("AppleLogin(Firebase) Success!")
                    println("- Email: $userEmail")
                    println("- Name: $userName")
                    println("- Token: ${firebaseIdToken.take(30)}...")

                    callback.invoke(
                        SocialToken(
                            accessToken = firebaseIdToken,
                            refreshToken = firebaseUid, // refreshToken 필드에 UID를 담아 서버로 전달 (Android와 통일)
                            email = userEmail,
                            name = userName
                        ),
                        null
                    )
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