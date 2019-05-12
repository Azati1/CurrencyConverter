package com.azati1.currencyconverter.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
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
import kotlinx.android.synthetic.main.currency_item_view.*
import kotlinx.android.synthetic.main.currency_item_view.view.*
import java.math.BigDecimal
import javax.inject.Inject

class CurrenciesScreen : MvpAppCompatActivity(), CurrenciesView, RecyclerAdapterCallback {

    @InjectPresenter
    lateinit var currenciesPresenter: CurrenciesPresenter

    @Inject
    lateinit var interactor : CurrenciesInteractor

    private var baseCurrency : String? = null
    private var baseCurrencyAmount = BigDecimal("100.0")

    lateinit var recyclerAdapter : RecyclerAdapter

    @ProvidePresenter
    fun provideCurrenciesPresenter() : CurrenciesPresenter {
        return CurrenciesPresenter(interactor)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MyApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        refreshLayout.setOnRefreshListener {
            currenciesPresenter.refresh()
        }
        mainCurrency.currencyRate.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


                if (p0 == "" || p0.isNullOrEmpty())
                    baseCurrencyAmount = BigDecimal.ZERO
                else
                    baseCurrencyAmount = BigDecimal(p0.toString())

                recyclerAdapter.recalculateCurrencies(baseCurrencyAmount)
                recyclerAdapter.notifyDataSetChanged()
            }

        })
        initRecyclerView()
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
        recyclerAdapter.clearData()
        baseCurrency = "EUR"
        baseCurrencyAmount = BigDecimal("100.0")
        currencyName.text = baseCurrency
        currencyRate.setText(baseCurrencyAmount.toEngineeringString())
    }

    override fun onItemClicked(currencyItemData: CurrencyItemData) {
        baseCurrency = currencyItemData.name
        mainCurrency.currencyName.text = baseCurrency
        Log.d("CDA", "new base " + currencyItemData.name)
        currenciesPresenter.loadCurrencies(baseCurrency)
    }

    private fun updateItemAdapterData(currencyData: CurrencyData) {
        Log.d("CDA", "amount is " + baseCurrencyAmount.toEngineeringString())
        recyclerAdapter = RecyclerAdapter(currencyData, baseCurrencyAmount, this)
        recycler.adapter = recyclerAdapter
        recyclerAdapter.updateData(currencyData)
        recyclerAdapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        val horizontalLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = horizontalLayoutManager
    }

}