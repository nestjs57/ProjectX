package com.arnoract.projectx.core.db

import android.content.Context
import androidx.room.Room
import com.arnoract.projectx.core.db.migration.MIGRATION_2_TO_3
import com.arnoract.projectx.core.db.migration.MIGRATION_3_TO_4

class DatabaseBuilder(private val context: Context) {
    fun buildRoomDatabaseStorage(): RoomDatabaseStorage {
        return Room.databaseBuilder(
            context, RoomDatabaseStorage::class.java, "room-database-storage"
        ).addMigrations(MIGRATION_2_TO_3, MIGRATION_3_TO_4).build()
    }
}