package com.kal.beum.write.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WritingEntity(
    @PrimaryKey(autoGenerate = false) val id: Int = 1,
    val title: String,
    val content: String,
    val tags: String,
    val isDevil: Boolean,
    val categoryId: Int,
    val category: String,
    val rewardPoint: Int
)

