package com.kal.beum.login.data.client

import com.kal.beum.login.domain.LoginClient

actual class NaverLoginClient actual constructor(obj: Any?) :
    LoginClient {
    actual override fun login(
        type: Int,
        callback: (Result<String>) -> Unit
    ) {
    }

    actual override fun logout(type: Int) {
    }
}