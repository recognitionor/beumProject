package com.kal.beum.main.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppEntity(
    @PrimaryKey(autoGenerate = false) val id: Int = 1, val isOnBoardingDone: Boolean
)
