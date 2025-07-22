package com.kal.beum.myinfo.data.database

import com.kal.beum.myinfo.domain.MyInfo

class MockMyInfoDao : MyInfoDao {
    override fun getMyInfo(): MyInfo {
        return MyInfo(
            id = 1,
            nickName = "닠네이무",
            angelPoint = 12800,
            devilPoint = 5990,
            accessToken = "mockAccessToken",
            refreshToken = "mockRefreshToken",
            profileImage = "",
            email = "jhlee@google.com",
            socialType = "KAKAO"
        )
    }
}