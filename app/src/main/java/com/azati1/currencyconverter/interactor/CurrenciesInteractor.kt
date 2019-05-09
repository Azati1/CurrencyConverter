package com.azati1.currencyconverter.interactor

import com.azati1.currencyconverter.model.CurrencyModel
import rx.Observable

interface CurrenciesInteractor {

    fun getCurrencies(base: String) :
            Observable<CurrencyModel>

}