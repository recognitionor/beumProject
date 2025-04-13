package com.kal.beum.main.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.kal.beum.core.data.database.StringListTypeConverter

@Database(
    entities = [AppEntity::class, UserInfoEntity::class], version = 2
)
@TypeConverters(
    StringListTypeConverter::class
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val appDao: AppDao

    companion object {
        const val DB_NAME = "beum_app.db"
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL("ALTER TABLE AppEntity ADD COLUMN isDevil INTEGER NOT NULL DEFAULT 0")
    }
}