package com.kal.beum.notice.domain

import kotlinx.serialization.Serializable


@Serializable
data class NoticeData(val id: Int, val noticeType: String, val content: String, val createdAt: String)
