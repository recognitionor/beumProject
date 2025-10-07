package com.kal.beum.login.data.client

import com.kal.beum.core.domain.DataError
import com.kal.beum.login.domain.LoginClient
import com.kal.beum.login.domain.SocialToken
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback


actual class NaverLoginClient actual constructor(private val obj: Any?) : LoginClient {

    actual override fun login(
        type: Int,
        callback: (SocialToken?, DataError.Remote?) -> Unit
    ) {
        println("NaverLoginClient login")
        val context = obj as? android.content.Context
        context?.let {
            NaverIdLoginSDK.authenticate(it, object : OAuthLoginCallback {
                override fun onSuccess() {
                    val accessToken = NaverIdLoginSDK.getAccessToken()
                    val refreshToken = NaverIdLoginSDK.getRefreshToken()
                    if (accessToken != null && accessToken.isNotEmpty() && refreshToken != null && refreshToken.isNotEmpty()) {
                        callback(
                            SocialToken(
                                accessToken = accessToken,
                                refreshToken = refreshToken
                            ), null
                        )
                    } else {
                        callback(null, DataError.Remote.LOGIN_FAILED)
                    }
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                    println("NaverIdLoginSDK.onFailure errorDescription")
                }

                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                    println("NaverIdLoginSDK.onError")
                }
            })
        }
    }

    actual override fun logout() {
        NaverIdLoginSDK.logout()
    }
}