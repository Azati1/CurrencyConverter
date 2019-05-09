package com.azati1.currencyconverter.data

import rx.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesApi {

    @GET("/latest")
    fun getCurrencies(@Query("base") base: String?):
            Observable<CurrencyResponse>

}
