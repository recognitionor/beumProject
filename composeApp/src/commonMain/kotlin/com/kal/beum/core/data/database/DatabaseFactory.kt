package com.kal.beum.core.data.database

import androidx.room.RoomDatabase
import com.kal.beum.main.data.database.AppDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<AppDatabase>
}