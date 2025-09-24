package com.kal.beum.write.domain

data class WritingData(
    val title: String,
    val content: String,
    val category: WritingCategory,
    val rewardPoint: Int,
    val tags: List<String>,
    val devil: Boolean
)
