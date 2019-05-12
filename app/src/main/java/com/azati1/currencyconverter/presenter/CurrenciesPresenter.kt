package com.azati1.currencyconverter.presenter

import android.icu.util.CurrencyAmount
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.azati1.currencyconverter.data.CurrencyData
import com.azati1.currencyconverter.interactor.CurrenciesInteractor
import com.azati1.currencyconverter.view.CurrenciesView
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.math.BigDecimal

@InjectViewState
class CurrenciesPresenter (private val interactor: CurrenciesInteractor) : MvpPresenter<CurrenciesView>() {

    private var currenciesSubscription: Subscription? = null

    fun loadCurrencies(base: String?) {
        viewState.showProgress()
        currenciesSubscription = interactor
            .getCurrencies(base)
            //.delay(1000, TimeUnit.MILLISECONDS)
            //.repeat()
            .map { currencyModel -> CurrencyData(currencyModel.base, currencyModel.rates) }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::loadCurrenciesSuccess, this::loadCurrenciesError)
    }

    private fun loadCurrenciesSuccess(currencyData: CurrencyData) {
        viewState.showCurrencies(currencyData)
    }

    private fun loadCurrenciesError(throwable: Throwable) {
        viewState.showError(throwable)
        currenciesSubscription!!.unsubscribe()
    }

    fun refresh() {
        viewState.clearData()
        loadCurrencies(null)
    }

}