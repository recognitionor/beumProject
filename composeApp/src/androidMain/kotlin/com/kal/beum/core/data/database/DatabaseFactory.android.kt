package com.kal.beum.core.data.database

import androidx.room.RoomDatabase
import com.kal.beum.main.data.database.AppDatabase

actual class DatabaseFactory(
    private val context: android.content.Context
) {
    actual fun create(): RoomDatabase.Builder<AppDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(AppDatabase.DB_NAME)

        return androidx.room.Room.databaseBuilder<AppDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )

    }
}