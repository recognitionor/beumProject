package com.kal.beum.login.data.client

import android.content.Context
import com.kal.beum.login.domain.LoginClient
import com.navercorp.nid.NaverIdLoginSDK

actual class KaKaoLoginClient actual constructor(private val obj: Any?) : LoginClient {
    actual override fun login(
        type: Int,
        callback: (Result<String>) -> Unit
    ) {
        println("KaKaoLoginClient : login")

    }

    actual override fun logout(type: Int) {

    }
}