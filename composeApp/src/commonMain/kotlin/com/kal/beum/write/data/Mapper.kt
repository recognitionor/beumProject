package com.kal.beum.write.data

import com.kal.beum.write.data.database.WritingEntity
import com.kal.beum.write.domain.WritingCategory
import com.kal.beum.write.domain.WritingData


fun CategoryGroupDto.toWriteCategory(): WritingCategory {
    return WritingCategory(
        categoryId = this.id, category = this.category
    )
}


fun WritingEntity.toWritingData(): WritingData {

    return WritingData(
        title = this.title,
        content = this.content,
        category = WritingCategory(this.categoryId, this.category),
        rewardPoint = this.rewardPoint,
        tags = this.tags,
        devil = this.isDevil
    )
}

