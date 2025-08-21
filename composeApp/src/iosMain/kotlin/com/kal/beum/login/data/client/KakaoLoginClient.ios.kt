package com.kal.beum.login.data.client

import com.kal.beum.core.domain.DataError
import com.kal.beum.login.domain.LoginClient
import com.kal.beum.login.domain.SocialToken

actual class KaKaoLoginClient actual constructor(obj: Any?) : LoginClient {
    actual override fun login(
        type: Int,
        callback: (SocialToken?, DataError.Remote?) -> Unit
    ) {
    }


    actual override fun logout() {
    }
}