package com.sfeatherstone.starlingroundup

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.sfeatherstone.starlingroundup.di.networkModule
import com.sfeatherstone.starlingroundup.di.repositoryModule
import com.sfeatherstone.starlingroundup.di.useCaseModule
import com.sfeatherstone.starlingroundup.di.viewModelModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(koinModules)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        val koinModules = listOf(
            networkModule,
            repositoryModule,
            useCaseModule,
            viewModelModule
        )

    }
}