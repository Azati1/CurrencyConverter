package com.azati1.currencyconverter.di.components

import com.azati1.currencyconverter.di.modules.AppModule
import com.azati1.currencyconverter.view.CurrenciesScreen
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(AppModule::class))

@Singleton
interface AppComponent {
    fun inject(activity: CurrenciesScreen)
}