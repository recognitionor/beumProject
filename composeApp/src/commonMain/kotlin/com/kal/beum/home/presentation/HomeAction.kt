package com.kal.beum.home.presentation


sealed interface HomeAction {
    data class OnLoadedHomeCommentList(val index: Int) : HomeAction
    data class OnToggleChange(val isDevil: Boolean) : HomeAction
    data class OnWriteConcernClick(val isDevil: Boolean) : HomeAction
    data class OnConcernSelected(val id: Int): HomeAction
}