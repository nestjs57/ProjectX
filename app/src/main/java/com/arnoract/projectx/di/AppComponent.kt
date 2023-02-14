package com.arnoract.projectx.di

import com.arnoract.projectx.core.sharedPreferencesModule
import org.koin.core.context.loadKoinModules

object AppComponent {
    fun init() = loadKoinModules(
        listOf(
            coreModule,
            apiModule,
            databaseModule,
            apiModule,
            sharedPreferencesModule
        )
    )
}