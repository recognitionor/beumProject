package com.kal.beum.community.presentation

import com.kal.beum.content.domain.CommentDetail
import com.kal.beum.content.domain.ContentDetail

sealed interface CommunityAction {
    data class OnCategoryGroupSelected(val categoryGroupName: String, val isDevil: Boolean) : CommunityAction

    data class OnCategorySelected(val index: Int, val isDevil: Boolean) : CommunityAction
    data class OnContentLikeClicked(val contentDetail: ContentDetail) : CommunityAction
    data class OnCommentLikeClicked(val commentDetail: CommentDetail) : CommunityAction
    data class LoadMoreComments(val commentDetail: CommentDetail) : CommunityAction

}