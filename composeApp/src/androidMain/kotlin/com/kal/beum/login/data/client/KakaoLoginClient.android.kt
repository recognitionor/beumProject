package com.kal.beum.login.data.client

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kal.beum.core.domain.DataError
import com.kal.beum.login.domain.LoginClient
import com.kal.beum.login.domain.SocialToken

actual class KaKaoLoginClient actual constructor(private val obj: Any?) : LoginClient {

    actual override fun login(
        type: Int, callback: (SocialToken?, DataError.Remote?) -> Unit
    ) {
        println("KaKaoLoginClient-login")
        val ctx = obj as Context
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                println("KaKaoLoginClient-login-failed : ${error.message}")
                callback.invoke(null, DataError.Remote.LOGIN_FAILED)
            } else if (token != null) {
                println("KaKaoLoginClient-login-success")
                callback.invoke(SocialToken(token!!.accessToken, token!!.refreshToken), null)
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(ctx)) {
            UserApiClient.instance.loginWithKakaoTalk(ctx) { token, error ->
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        callback.invoke(null, error)
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(ctx, callback = callback)
                } else if (token != null) {
                    callback.invoke(token, null)
                }
            }
        } else {
            println("KaKaoLoginClient-else")
            UserApiClient.instance.loginWithKakaoAccount(ctx, callback = callback)
        }
    }


    actual override fun logout() {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e(this.javaClass.simpleName, "로그아웃 실패. SDK에서 토큰 폐기됨", error)
            } else {
                Log.i(this.javaClass.simpleName, "로그아웃 성공. SDK에서 토큰 폐기됨")
            }
        }
    }
}