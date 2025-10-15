package com.kal.beum.content.presentation

import com.kal.beum.content.domain.CommentInfo

data class ReplyStateState(
    val replyInfo: CommentInfo? = null,
    val isLikeMe: Boolean = false

)