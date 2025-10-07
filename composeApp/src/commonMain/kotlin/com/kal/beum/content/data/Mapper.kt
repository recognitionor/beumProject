package com.kal.beum.content.data

import com.kal.beum.community.data.dto.CategoryDto
import com.kal.beum.community.data.dto.CommunityItemDto
import com.kal.beum.community.domain.Category
import com.kal.beum.community.domain.CommunityItem
import com.kal.beum.content.data.dto.CommentInfoDto
import com.kal.beum.content.domain.CommentInfo

fun CategoryDto.toCategoryData(): Category {
    return Category(
        id = this.id, category = this.name
    )
}

fun CommentInfoDto.toCommentInfo(): CommentInfo {
    return CommentInfo(
        boardId = this.boardId,
        commentCount = this.commentCount,
        comments = this.comments
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
