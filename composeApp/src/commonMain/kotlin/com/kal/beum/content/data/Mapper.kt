package com.kal.beum.content.data

import com.kal.beum.community.data.dto.CategoryDto
import com.kal.beum.community.data.dto.CommunityItemDto
import com.kal.beum.community.domain.Category
import com.kal.beum.community.domain.CommunityItem
import com.kal.beum.content.data.dto.ContentDetailDto
import com.kal.beum.content.data.dto.ReplyInfoDto
import com.kal.beum.content.domain.ContentDetail
import com.kal.beum.content.domain.ReplyInfo

fun CategoryDto.toCategoryData(): Category {
    return Category(
        id = this.id, category = this.name
    )
}


fun CommunityItemDto.toCommunityItem(): CommunityItem {
    return CommunityItem(
        id = this.id,
        title = this.title,
        content = this.content,
        writer = this.writer,
        categoryName = this.categoryName,
        isPopular = this.isPopular,
        lastModifiedTime = this.lastModifiedTime.toString()
    )
}

fun ReplyInfoDto.toReplyInfo(): ReplyInfo {
    return ReplyInfo(
        writer = this.writer,
        content = this.content,
        likeCount = this.isLiked,
        isSelected = this.isSelected,
        replyList = this.replyList.map { it.toReplyInfo() },
        lastModifiedTime = this.lastModifiedTime,
    )
}

fun ContentDetailDto.toContentDetail(): ContentDetail {
    return ContentDetail(id = this.id,
        title = this.title,
        content = this.content,
        writer = this.writer,
        categoryName = this.categoryName,
        rewardPoint = this.rewardPoint,
        isDevil = this.isDevil,
        tags = this.tags,
        likeCount = this.likeCount,
        viewCount = this.viewCount,
        lastModifiedTime = this.lastModifiedTime,
        replyList = this.replyList.map { it.toReplyInfo() })
}