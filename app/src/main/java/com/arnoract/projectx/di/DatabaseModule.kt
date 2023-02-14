package com.arnoract.projectx.di

import com.arnoract.projectx.core.db.DatabaseBuilder
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { DatabaseBuilder(androidApplication()).buildRoomDatabaseStorage() }
}