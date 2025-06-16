package com.kal.beum.write.data

import com.kal.beum.write.domain.WritingCategory


fun CategoryGroupDto.toWriteCategory(): WritingCategory {
    return WritingCategory(
        categoryId = this.id, category = this.category
    )
}