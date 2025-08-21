package com.kal.beum.login.data.client

import com.kal.beum.core.domain.DataError
import com.kal.beum.login.domain.LoginClient
import com.kal.beum.login.domain.SocialToken

expect class NaverLoginClient(obj: Any?) : LoginClient {

    override fun login(
        type: Int,
        callback: (SocialToken?, DataError.Remote?) -> Unit
    )

    override fun logout()
}