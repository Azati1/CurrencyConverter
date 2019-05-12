package com.azati1.currencyconverter.di.components

import com.azati1.currencyconverter.di.modules.AppModules
import com.azati1.currencyconverter.view.CurrenciesScreen
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(AppModules::class))

@Singleton
interface AppComponent {
    fun inject(activity: CurrenciesScreen)
}