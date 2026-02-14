package com.kal.beum.notice.presentaion

import com.kal.beum.notice.domain.NoticeCategory
import com.kal.beum.notice.domain.NoticeData
import com.kal.beum.notice.domain.NoticeMap

data class NoticeState(
    val categoryList: List<NoticeCategory> = emptyList(),
    val filteredNoticeList: List<NoticeData> = emptyList(),
    val noticeList: List<NoticeData> = emptyList(),
    val noticeMap: NoticeMap? = null,
    val selectedIndex: Int = 0,
    val isNotificationEnabled: Boolean = true
)
