package com.kal.beum.notice.data

import com.kal.beum.notice.data.dto.NoticeCategoryDto
import com.kal.beum.notice.data.dto.NoticeDto
import com.kal.beum.notice.domain.NoticeCategory
import com.kal.beum.notice.domain.NoticeData

fun NoticeDto.toNoticeData(): NoticeData {
    return NoticeData(
        id = this.id, category = this.category, content = this.content, createdAt = this.createdAt
    )
}

fun NoticeCategoryDto.toNoticeCategoryData(): NoticeCategory {
    return NoticeCategory(
        id = this.id, category = this.category
    )
}