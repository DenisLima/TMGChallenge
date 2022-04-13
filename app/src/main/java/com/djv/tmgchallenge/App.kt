package com.djv.tmgchallenge

import android.app.Application
import com.djv.tmgchallenge.dagger.component.DaggerLibraryComponent
import com.djv.tmgchallenge.dagger.component.LibraryComponent
import com.djv.tmgchallenge.dagger.module.RoomDatabaseModule

class App: Application() {

    companion object {
        lateinit var instance: App
    }

    lateinit var libraryComponent: LibraryComponent

    override fun onCreate() {
        super.onCreate()
        instance = this

        libraryComponent = DaggerLibraryComponent
            .builder()
            .roomDatabaseModule(RoomDatabaseModule(this))
            .build()
    }
}