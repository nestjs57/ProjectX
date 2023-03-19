package com.arnoract.projectx.core

import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val sharedPreferencesModule = module {
    single(named(KoinConst.SharedPreference.DEFAULT)) {
        SharedPreferencesBuilder(androidApplication()).buildDefault()
    }
    single(named(KoinConst.SharedPreference.READER_PREFERENCE)) {
        SharedPreferencesBuilder(androidApplication()).buildUserPreference()
    }
}
