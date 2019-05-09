package com.azati1.currencyconverter.model

import com.azati1.currencyconverter.data.CurrencyResponse
import rx.Observable

interface CurrencyDataProvider {

    fun getCurrencies(base: String?):
            Observable<CurrencyResponse>

}