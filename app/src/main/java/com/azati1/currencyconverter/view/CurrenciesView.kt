package com.azati1.currencyconverter.view

import com.arellomobile.mvp.MvpView
import com.azati1.currencyconverter.data.CurrencyData

interface CurrenciesView : MvpView {
    fun showProgress()
    fun showError(e: Throwable)
    fun showCurrencies(currencyData: CurrencyData)
    fun clearData()
}
