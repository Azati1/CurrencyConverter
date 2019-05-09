package com.azati1.currencyconverter

import android.app.Application
import com.azati1.currencyconverter.di.components.AppComponent
import com.azati1.currencyconverter.di.components.DaggerAppComponent
import com.azati1.currencyconverter.di.modules.AppModule

class MyApp : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule())
            .build()
    }

}