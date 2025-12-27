package com.kal.beum.community.presentation

import com.kal.beum.community.domain.Category
import com.kal.beum.community.domain.CategoryMap
import com.kal.beum.community.domain.CommunityItem
import com.kal.beum.main.presentation.FullScreenType
import com.kal.beum.write.domain.WritingData

data class CommunityState(
    val categoryGroupList: List<String> = emptyList(),
    val categoryList: List<Category> = emptyList(),
    val categoryOriginalMap: CategoryMap? = null,
    val selectedCategoryGroupId: String = "",
    val selectedCategoryId: Int = 0,
    val communityListPage: Int = 0,
    val communityList: List<List<CommunityItem>> = emptyList(),
    val communityListTemp: List<CommunityItem> = emptyList(),
    val writingTemp: WritingData? = null,
    val isDraftDialog: Boolean = false,
    val onProgress: Boolean = false,
    val onError: String? = null
)