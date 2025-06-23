package com.kal.beum.write.presentation

import com.kal.beum.write.domain.WritingCategory

data class WritingState(
    val writeCategoryMap: Map<String, List<WritingCategory>> = emptyMap(),
    val title: String = "",
    val content: String = "",
    val tags: String = "",
    val isDevil: Boolean = false,
    val selectedCategory: WritingCategory? = null,
    val rewardPoint: Int = 0,
    val submitProgress: Boolean = false,
    val submitError: String = "",
    val isClose: Boolean = false,
    val closeMessage: String? = null
)