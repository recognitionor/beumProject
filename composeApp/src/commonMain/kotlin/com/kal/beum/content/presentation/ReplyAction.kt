package com.kal.beum.content.presentation

import com.kal.beum.content.domain.CommentDetail
import com.kal.beum.content.domain.CommentInfo

sealed interface ReplyAction {
    data class OnReplyLikeClicked(val replyDetail: CommentDetail) : ReplyAction

    data class OnSendReply(val replyDetail: CommentDetail, val content: String) : ReplyAction


}