package com.azati1.currencyconverter

import android.app.Application
import com.azati1.currencyconverter.data.CurrenciesApi
import com.azati1.currencyconverter.di.components.AppComponent
import com.azati1.currencyconverter.di.components.DaggerAppComponent
import com.azati1.currencyconverter.di.modules.AppModules
import com.azati1.currencyconverter.model.CurrencyDataProvider
import javax.inject.Inject

class MyApp : Application() {

    @Inject
    lateinit var api : CurrenciesApi

    @Inject
    lateinit var repository : CurrencyDataProvider

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
            .appModules(AppModules())
            .build()
    }

}