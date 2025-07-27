package com.kal.beum.notice.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class NoticeDto(val id: Int, val category: String, val content: String, val createdAt: Long)
