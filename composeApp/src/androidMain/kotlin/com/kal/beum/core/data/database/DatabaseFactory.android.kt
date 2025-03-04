package com.kal.beum.core.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(private val ctx: Context) {
    actual fun create(): RoomDatabase.Builder<AppDatabase> {
        val appCtx = ctx.applicationContext
        val dbFile = appCtx.getDatabasePath(AppDatabase.DB_NAME)
        return Room.databaseBuilder(context = appCtx, name = dbFile.absolutePath)
    }
}