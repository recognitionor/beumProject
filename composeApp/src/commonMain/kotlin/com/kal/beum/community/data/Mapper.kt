package com.kal.beum.community.data

import com.kal.beum.community.data.dto.CategoryDto
import com.kal.beum.community.data.dto.CommunityCommentItemDto
import com.kal.beum.community.data.dto.CommunityDto
import com.kal.beum.community.data.dto.CommunityItemDto
import com.kal.beum.community.domain.Category
import com.kal.beum.community.domain.Community
import com.kal.beum.community.domain.CommunityItem

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
        writerId = this.writerId,
        writer = this.writer,
        categoryName = this.categoryName,
        isPopular = this.isPopular,
        lastModifiedTime = this.lastModifiedTime.toString()
    )
}

fun CommunityDto.toCommunity(): Community {
    return Community(
        page = this.page, size = this.size, boardList = this.boardList.map { it.toCommunityItem() })
}

fun CommunityCommentItemDto.toCommunityItem(): CommunityItem {
    return CommunityItem(
        id = this.id,
        title = this.title,
        content = this.content,
        writerId = this.writerId,
        writer = this.writer,
        categoryName = this.categoryName,
        isPopular = this.like,
        lastModifiedTime = this.createTime
    )
}