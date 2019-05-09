package com.azati1.currencyconverter.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.azati1.currencyconverter.CurrencyItemData
import com.azati1.currencyconverter.MyApp
import com.azati1.currencyconverter.R
import com.azati1.currencyconverter.data.CurrenciesApi
import com.azati1.currencyconverter.data.CurrencyData
import com.azati1.currencyconverter.interactor.CurrenciesInteractor
import com.azati1.currencyconverter.model.CurrencyDataProvider
import com.azati1.currencyconverter.presenter.CurrenciesPresenter
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import javax.inject.Inject

class CurrenciesScreen : MvpAppCompatActivity(), CurrenciesView, RecyclerAdapterCallback {

    lateinit var recyclerAdapter : RecyclerAdapter

    @InjectPresenter
    lateinit var currenciesPresenter: CurrenciesPresenter

    @Inject lateinit var api : CurrenciesApi
    @Inject lateinit var repository : CurrencyDataProvider
    @Inject lateinit var interactor : CurrenciesInteractor

    private var baseCurrency = "RUB"
    private var baseCurrencyAmount = BigDecimal("1.0")

    @ProvidePresenter
    fun provideCurrenciesPresenter() : CurrenciesPresenter {
        return CurrenciesPresenter(interactor)
    }

    private fun refreshData() {
        currenciesPresenter.loadCurrencies(baseCurrency)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MyApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        refreshLayout.setOnRefreshListener {
            refreshData()
        }
        initRecyclerView()
        //currenciesPresenter.refresh()
        currenciesPresenter.loadCurrencies(baseCurrency)
    }

    override fun showProgress() {
        refreshLayout.isRefreshing = true
    }

    override fun showError(e: Throwable) {
        Log.d("CDA", "some error")
        if (refreshLayout.isRefreshing)
            refreshLayout.isRefreshing = false
    }

    override fun showCurrencies(currencyData: CurrencyData) {
        updateItemAdapterData(currencyData)
        if (refreshLayout.isRefreshing)
            refreshLayout.isRefreshing = false
    }

    override fun clearData() {

    }

    override fun onItemClicked(currencyItemData: CurrencyItemData) {
        baseCurrency = currencyItemData.name
        refreshData()
    }

    private fun updateItemAdapterData(currencyData: CurrencyData) {
        recyclerAdapter = RecyclerAdapter(currencyData, baseCurrencyAmount,this)
        recycler.adapter = recyclerAdapter
        recyclerAdapter.updateData(currencyData)
        recyclerAdapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        val horizontalLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = horizontalLayoutManager
    }

}