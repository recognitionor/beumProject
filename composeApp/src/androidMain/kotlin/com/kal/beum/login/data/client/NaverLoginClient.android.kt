package com.kal.beum.login.data.client

import com.kal.beum.login.domain.LoginClient
import com.navercorp.nid.NaverIdLoginSDK

actual class NaverLoginClient actual constructor(private val obj: Any?) :
    LoginClient {
    actual override fun login(
        type: Int,
        callback: (Result<String>) -> Unit
    ) {
        println("NaverLoginClient.login")
        val context = obj as? android.content.Context
        context?.let {
            NaverIdLoginSDK.initialize(context, "t9ZJsIIvos3W2IqPRVWg", "oD1SqxgLnE", "비움")
            println("NaverIdLoginSDK.isInitialized() : ${NaverIdLoginSDK.isInitialized()}")

        }

    }

    actual override fun logout(type: Int) {
    }
}