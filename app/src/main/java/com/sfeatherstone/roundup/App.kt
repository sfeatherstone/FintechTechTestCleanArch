package com.sfeatherstone.roundup

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.sfeatherstone.roundup.di.networkModule
import com.sfeatherstone.roundup.di.repositoryModule
import com.sfeatherstone.roundup.di.useCaseModule
import com.sfeatherstone.roundup.di.viewModelModule
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