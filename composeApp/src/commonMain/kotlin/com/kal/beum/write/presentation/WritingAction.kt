package com.kal.beum.write.presentation

import com.kal.beum.write.domain.WritingCategory

sealed interface WritingAction {
    data object Reset : WritingAction
    data object Submit : WritingAction
    data object Close : WritingAction
    data class OnTitleChanged(val newTitle: String) : WritingAction
    data class OnContentChanged(val newContent: String) : WritingAction
    data class OnTagChanged(val newTag: String) : WritingAction
    data class OnCommunityChanged(val isDevil: Boolean) : WritingAction
    data class OnCategoryChanged(val writingCategory: WritingCategory) : WritingAction
    data class OnPointChanged(val point: Int) : WritingAction
}