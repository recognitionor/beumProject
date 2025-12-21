package com.kal.beum.notice.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class NoticeDto(val noticeId: Int, val content: String, val noticeType: String, val createTime: String)
