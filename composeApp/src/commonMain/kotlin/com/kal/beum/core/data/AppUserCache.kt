package com.kal.beum.core.data

import com.kal.beum.main.domain.UserInfo

object AppUserCache {
    var accessToken: String? = null
    var isDevil: Boolean = false
    var updateFCMToken: String? = null

    var userInfo: UserInfo? = null
}

