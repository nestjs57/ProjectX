package com.arnoract.projectx.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arnoract.projectx.core.db.dao.ArticleDao
import com.arnoract.projectx.core.db.entity.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 2
)
@TypeConverters(DateConverter::class, LongListConverter::class)
abstract class RoomDatabaseStorage : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}