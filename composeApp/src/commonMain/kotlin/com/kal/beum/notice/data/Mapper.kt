package com.kal.beum.notice.data

import com.kal.beum.notice.data.dto.NoticeCategoryDto
import com.kal.beum.notice.data.dto.NoticeDto
import com.kal.beum.notice.data.dto.NoticeMapDto
import com.kal.beum.notice.domain.NoticeCategory
import com.kal.beum.notice.domain.NoticeData
import com.kal.beum.notice.domain.NoticeMap

fun NoticeDto.toNoticeData(): NoticeData {
    return NoticeData(
        id = this.noticeId, noticeType = this.noticeType, content = this.content, createdAt = this.createTime
    )
}

fun NoticeMapDto.toNoticeMap(): NoticeMap {
    return NoticeMap(
        noticeMap = this.noticeMap.mapValues { (_, noticeList) ->
            noticeList.map { it.toNoticeData() }
        }
    )
}

fun NoticeCategoryDto.toNoticeCategoryData(): NoticeCategory {
    return NoticeCategory(
        id = this.id, category = this.category
    )
}

