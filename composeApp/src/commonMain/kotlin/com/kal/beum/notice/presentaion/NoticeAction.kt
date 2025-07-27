package com.kal.beum.notice.presentaion

sealed interface NoticeAction {
    data class FilterNotice(val index: Int) : NoticeAction
}