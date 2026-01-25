package com.kal.beum.community.data

import com.kal.beum.community.data.dto.CategoryDto
import com.kal.beum.community.data.dto.CategoryMapDto
import com.kal.beum.community.data.dto.CommunityCommentItemDto
import com.kal.beum.community.data.dto.CommunityDto
import com.kal.beum.community.data.dto.CommunityItemDto
import com.kal.beum.community.domain.Category
import com.kal.beum.community.domain.CategoryMap
import com.kal.beum.community.domain.Community
import com.kal.beum.community.domain.CommunityItem

fun CategoryDto.toCategoryData(): Category {
    return Category(
        id = this.id, name = this.name, groupName = this.groupName
    )
}


fun CategoryMapDto.toCategoryMap(): CategoryMap {
    return CategoryMap(
        categoryMap = this.categoryMap.mapValues { (_, categoryList) ->
            categoryList.map { it.toCategoryData() }
        })
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
        replyCount = this.replyCount,
        likeCount = this.likeCount,
        lastModifiedTime = this.createTime
    )
}