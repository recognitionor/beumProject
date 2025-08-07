package com.kal.beum.login.data.client

import com.kal.beum.login.domain.LoginClient

expect class KaKaoLoginClient(obj: Any?) : LoginClient {
    override fun login(
        type: Int,
        callback: (Result<String>) -> Unit

    )

    override fun logout(type: Int)
}