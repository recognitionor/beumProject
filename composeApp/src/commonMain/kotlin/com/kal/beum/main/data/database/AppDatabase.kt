package com.kal.beum.main.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.kal.beum.core.data.database.StringListTypeConverter
import com.kal.beum.write.data.database.WritingDao
import com.kal.beum.write.data.database.WritingEntity

@Database(
    entities = [AppEntity::class, UserInfoEntity::class, WritingEntity::class], version = 4
)
@TypeConverters(
    StringListTypeConverter::class
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val appDao: AppDao

    abstract val writingDao: WritingDao

    companion object {
        const val DB_NAME = "beum_app.db"
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL("ALTER TABLE AppEntity ADD COLUMN isDevil INTEGER NOT NULL DEFAULT 0")
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(connection: SQLiteConnection) {
        // UserInfoEntity 테이블명은 @Entity에 tableName 미지정 시 클래스명 사용
        connection.execSQL(
            "ALTER TABLE UserInfoEntity ADD COLUMN angelPoint INTEGER NOT NULL DEFAULT 0"
        )
        connection.execSQL(
            "ALTER TABLE UserInfoEntity ADD COLUMN devilPoint INTEGER NOT NULL DEFAULT 0"
        )
    }
}