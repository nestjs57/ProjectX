package com.arnoract.projectx.core.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_TO_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE article ADD COLUMN firstDate INTEGER NOT NULL DEFAULT 0"
        )
        database.execSQL(
            "ALTER TABLE article ADD COLUMN lastDate INTEGER NOT NULL DEFAULT 0"
        )
    }
}