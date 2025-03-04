package com.kal.beum.core.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val version: String,
)