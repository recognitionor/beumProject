package com.kal.beum.community.presentation

sealed interface CommunityAction {
    data class OnTabSelected(val index: Int, val isDevil: Boolean) : CommunityAction
    object GetTempWriting : CommunityAction
    object OnDraftDialog : CommunityAction
}