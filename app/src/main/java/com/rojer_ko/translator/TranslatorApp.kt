package com.rojer_ko.translator

import android.app.Application
import com.rojer_ko.translator.di.application
import com.rojer_ko.translator.di.historyScreen
import com.rojer_ko.translator.di.mainScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TranslatorApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin { androidContext(this@TranslatorApp)
            modules(listOf(application, mainScreen, historyScreen)) }
    }
}