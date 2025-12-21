package com.kal.beum.notice.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class NoticeMapDto(
    val noticeMap: Map<String, List<NoticeDto>>,
)
