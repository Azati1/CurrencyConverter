package com.azati1.currencyconverter.model

import com.azati1.currencyconverter.data.CurrenciesApi
import com.azati1.currencyconverter.data.CurrencyResponse
import rx.Observable

class CurrencyDataProviderImpl (private val api: CurrenciesApi) : CurrencyDataProvider {

    override fun getCurrencies(base: String?): Observable<CurrencyResponse> {
        return api.getCurrencies(base)
    }

}