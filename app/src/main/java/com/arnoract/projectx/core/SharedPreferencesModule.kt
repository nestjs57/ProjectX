package com.arnoract.projectx.core

import com.arnoract.projectx.core.KoinConst
import com.arnoract.projectx.core.SharedPreferencesBuilder
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val sharedPreferencesModule = module {
    single(named(KoinConst.SharedPreference.DEFAULT)) {
        SharedPreferencesBuilder(androidApplication()).buildDefault()
    }
}
