package com.kal.beum.core.data.database

import androidx.room.RoomDatabase
import com.kal.beum.main.data.database.AppDatabase

actual class DatabaseFactory(
    private val context: _root_ide_package_.android.content.Context
) {
    actual fun create(): RoomDatabase.Builder<AppDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(AppDatabase.DB_NAME)

        return _root_ide_package_.androidx.room.Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}