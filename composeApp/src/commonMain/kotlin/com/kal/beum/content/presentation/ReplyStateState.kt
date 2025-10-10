package com.kal.beum.content.presentation

import com.kal.beum.community.domain.CommunityItem
import com.kal.beum.content.domain.CommentInfo
import com.kal.beum.write.domain.WritingData

data class ReplyStateState(
    val replyInfo: CommentInfo? = null
)