package com.djv.tmgchallenge

import android.app.Application
import com.djv.tmgchallenge.data.di.dataModule
import com.djv.tmgchallenge.domain.di.domainModule
import com.djv.tmgchallenge.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(dataModule, domainModule, uiModule))
        }
    }
}