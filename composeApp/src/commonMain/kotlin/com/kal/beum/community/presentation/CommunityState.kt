package com.kal.beum.community.presentation

import com.kal.beum.community.domain.Category
import com.kal.beum.community.domain.CommunityItem

data class CommunityState(
    val categoryList: List<Category> = emptyList(),
    val selectedCategoryId: Int = 0,
    val communityList: List<List<CommunityItem>> = emptyList()
)