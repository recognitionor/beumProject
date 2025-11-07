package com.kal.beum.content.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReportRequestDto(
    val boardId: Int,
    val reportContent: String,
    val reportId: Int,
    val reportType: String,
    val reportedUserId: String
)