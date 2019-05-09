package com.azati1.currencyconverter.interactor

import com.azati1.currencyconverter.model.CurrencyDataProvider
import com.azati1.currencyconverter.model.CurrencyModel
import rx.Observable

class CurrenciesInteractorImpl(private val repository : CurrencyDataProvider) : CurrenciesInteractor {

    override fun getCurrencies(base: String): Observable<CurrencyModel> {
        return repository.getCurrencies(base).map { CurrencyModel(it.base, it.rates) }
    }

}