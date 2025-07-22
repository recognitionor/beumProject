package com.kal.beum.myinfo.data

import com.kal.beum.community.data.dto.CommunityItemDto
import com.kal.beum.community.domain.CommunityItem
import com.kal.beum.myinfo.data.dto.MyContentDto
import com.kal.beum.myinfo.domain.MyContent

fun MyContentDto.toMyContent(): MyContent {
    return MyContent(
        id = this.id,
        title = this.title,
        content = this.content,
        category = this.category,
        createdAt = this.createdAt,
        likeCount = this.likeCount,
        replyCount = this.replyCount
    )
}