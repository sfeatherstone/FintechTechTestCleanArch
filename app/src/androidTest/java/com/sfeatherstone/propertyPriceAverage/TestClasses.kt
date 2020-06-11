package com.sfeatherstone.propertyPriceAverage

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()
/*
        startKoin{
            androidContext(this@TestApp)
            emptyList<Module>()
        }
*/
    }
}

class TestAppJUnitRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}
