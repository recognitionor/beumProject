package com.kal.beum.content.data

import com.kal.beum.community.data.dto.CategoryDto
import com.kal.beum.community.data.dto.CommunityItemDto
import com.kal.beum.community.domain.Category
import com.kal.beum.community.domain.CommunityItem
import com.kal.beum.content.data.dto.CommentDetailDto
import com.kal.beum.content.data.dto.CommentInfoDto
import com.kal.beum.content.domain.CommentDetail
import com.kal.beum.content.domain.CommentInfo
import com.kal.beum.content.domain.ContentDetail
import com.kal.beum.core.data.AppUserCache

fun CategoryDto.toCategoryData(): Category {
    return Category(
        id = this.id, name = this.name
    )
}

fun CommentInfoDto.toCommentInfo(): CommentInfo {
    return CommentInfo(
        boardId = this.boardId,
        commentCount = this.commentCount,
        comments = this.comments.map { dto -> dto.toContentDetail() })
}

fun CommentDetailDto.toContentDetail(): CommentDetail {
    return CommentDetail(
        boardId = this.boardId,
        content = this.content,
        depth = this.depth,
        id = this.id,
        likeCount = this.likeCount,
        likeIsMe = this.likeIsMe,
        ord = this.ord,
        parentId = this.parentId,
        reReplyCount = this.reReplyCount,
        createdAt = this.createdAt,
        user = this.user
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
