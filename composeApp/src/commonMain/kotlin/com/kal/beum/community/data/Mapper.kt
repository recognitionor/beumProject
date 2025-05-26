package com.kal.beum.community.data

import com.kal.beum.community.data.dto.CategoryDto
import com.kal.beum.community.data.dto.CommunityItemDto
import com.kal.beum.community.domain.Category
import com.kal.beum.community.domain.CommunityItem

fun CategoryDto.toCategoryData(): Category {
    return Category(
        id = this.id, category = this.category
    )
}


fun CommunityItemDto.toCommunityItem(): CommunityItem {
    return CommunityItem(
        id = this.id,
        title = this.title,
        content = this.content,
        writer = this.writer,
        categoryName = this.categoryName,
        isPopular = this.isPopular
    )
}