package com.kal.beum.community.presentation

import com.kal.beum.community.domain.Category
import com.kal.beum.community.domain.CommunityItem
import com.kal.beum.main.presentation.FullScreenType
import com.kal.beum.write.domain.WritingData

data class CommunityState(
    val categoryList: List<Category> = emptyList(),
    val selectedCategoryId: Int = 0,
    val communityList: List<List<CommunityItem>> = emptyList(),
    val writingTemp: WritingData? = null,
    val isDraftDialog: Boolean = false,
    val onProgress: Boolean = false
)