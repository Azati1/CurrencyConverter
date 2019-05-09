package com.azati1.currencyconverter.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.azati1.currencyconverter.data.CurrencyData
import com.azati1.currencyconverter.interactor.CurrenciesInteractor
import com.azati1.currencyconverter.view.CurrenciesView
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

@InjectViewState
class CurrenciesPresenter (private val interactor: CurrenciesInteractor) : MvpPresenter<CurrenciesView>() {

    private var currenciesSubscription: Subscription? = null

    fun loadCurrencies(base: String?) {
        viewState.showProgress()
        currenciesSubscription = interactor
            .getCurrencies(base!!)
            .map { currencyModel -> CurrencyData(currencyModel.base, currencyModel.rates!!.toMutableMap()) }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::loadCurrenciesSuccess, this::loadCurrenciesError)
    }

    private fun loadCurrenciesSuccess(currencyData: CurrencyData) {
        viewState.showCurrencies(currencyData)
    }

    private fun loadCurrenciesError(throwable: Throwable) {
        viewState.showError(throwable)
        unsubscribe(currenciesSubscription!!)
    }

    fun refresh() {
        viewState.clearData()
        loadCurrencies(null)
    }

    private fun unsubscribe(subscription: Subscription) {

    }
}