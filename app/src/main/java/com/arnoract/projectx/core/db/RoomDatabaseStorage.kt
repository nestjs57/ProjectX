package com.arnoract.projectx.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arnoract.projectx.core.db.DateConverter
import com.arnoract.projectx.core.db.LongListConverter

@Database(
    entities = [],
    version = 1
)
@TypeConverters(DateConverter::class, LongListConverter::class)
abstract class RoomDatabaseStorage : RoomDatabase() {

}