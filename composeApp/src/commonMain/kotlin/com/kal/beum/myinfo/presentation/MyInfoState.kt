package com.kal.beum.myinfo.presentation

import com.kal.beum.main.domain.UserInfo
import com.kal.beum.myinfo.domain.MyContent
import com.kal.beum.myinfo.domain.MyInfo

data class MyInfoState(
    val myInfo: UserInfo? = null,
    val myContent: List<MyContent> = emptyList(),
    val myReply: List<MyContent> = emptyList()
) {

    companion object {
        fun empty() = MyInfoState(
            myInfo = null, myContent = emptyList(), myReply = emptyList()
        )
    }
}
