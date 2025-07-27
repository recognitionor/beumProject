package com.kal.beum.notice.presentaion

import com.kal.beum.notice.domain.NoticeCategory
import com.kal.beum.notice.domain.NoticeData

data class NoticeState(
    val categoryList: List<NoticeCategory> = emptyList(),
    val filteredNoticeList: List<NoticeData> = emptyList(),
    val noticeList: List<NoticeData> = emptyList(),
    val selectedIndex: Int = 0
)
