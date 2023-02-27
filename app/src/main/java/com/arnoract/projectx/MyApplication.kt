package com.arnoract.projectx

import android.app.Application
import com.arnoract.projectx.di.AppComponent
import com.arnoract.projectx.domain.DomainComponent
import com.arnoract.projectx.ui.di.UiComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //Places.initialize(applicationContext, getString(R.string.google_maps_key), Locale.US)
        startKoin {
            androidContext(this@MyApplication)
        }
        AppComponent.init()
        UiComponent.init()
        DomainComponent.init()
    }
}