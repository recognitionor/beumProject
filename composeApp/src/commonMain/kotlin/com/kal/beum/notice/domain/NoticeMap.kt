package com.kal.beum.notice.domain

import com.kal.beum.notice.data.dto.NoticeDto
import kotlinx.serialization.Serializable

@Serializable
data class NoticeMap(
    val noticeMap: Map<String, List<NoticeData>>,
)
