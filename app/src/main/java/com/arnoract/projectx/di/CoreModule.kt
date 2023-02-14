package com.arnoract.projectx.di

import com.arnoract.projectx.common.time.DefaultTimeProvider
import com.arnoract.projectx.common.time.TimeProvider
import com.arnoract.projectx.core.CoroutinesDispatcherProvider
import com.arnoract.projectx.core.MyGsonBuilder
import com.arnoract.projectx.core.OkHttpBuilder
import com.arnoract.projectx.core.RetrofitBuilder
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val coreModule = module {
    single { MyGsonBuilder().build() }
    single { OkHttpBuilder(androidApplication()).build() }
    single { RetrofitBuilder(get(), get()).build() }
    single { CoroutinesDispatcherProvider() }
    single<TimeProvider> { DefaultTimeProvider() }
}