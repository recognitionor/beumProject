package com.kal.beum.login.domain

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.main.domain.UserInfo

interface LoginClient {
    fun login(type: Int, callback: (SocialToken?, DataError.Remote?) -> Unit)
    fun logout()
}