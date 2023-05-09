package com.arnoract.projectx.core.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_3_TO_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE article ADD COLUMN totalReadTime INTEGER NOT NULL DEFAULT 0"
        )
    }
}