package com.kal.beum.content.presentation

import com.kal.beum.content.domain.CommentDetail

sealed interface ReplyAction {
    data class OnReplyLikeClicked(val replyDetail: CommentDetail) : ReplyAction

    data class OnSubReplyLikeClicked(val replyDetail: CommentDetail) : ReplyAction

    data class OnSendReply(val replyDetail: CommentDetail, val content: String) : ReplyAction

    data class PickComment(val targetUserId: String, val boardId: String) : ReplyAction
}