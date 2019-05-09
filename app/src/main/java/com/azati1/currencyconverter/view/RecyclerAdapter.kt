package com.azati1.currencyconverter.view

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.azati1.currencyconverter.CurrencyItemData
import com.azati1.currencyconverter.R
import com.azati1.currencyconverter.data.CurrencyData
import kotlinx.android.synthetic.main.currency_item_view.view.*
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

class RecyclerAdapter(private var currencyData: CurrencyData, private var baseCurrencyAmount: BigDecimal, private val listener : RecyclerAdapterCallback) : RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>() {

    private var rates : LinkedList<CurrencyItemData>

    init {
        if (currencyData.base == null || currencyData.base == "EUR") {
            currencyData.rates!!["EUR"] = baseCurrencyAmount
        }
        rates = LinkedList(currencyData.rates!!.map { CurrencyItemData(it.key, it.value) })
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ItemViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.currency_item_view, viewGroup, false)
        return RecyclerAdapter(currencyData, baseCurrencyAmount, listener).ItemViewHolder(v)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, i: Int) {
        holder.bind(rates[i])
    }

    override fun getItemCount(): Int {
        return rates.size
    }

    fun updateData(currencyData: CurrencyData) {
        Log.d("CDA", "currencyData rates size " + currencyData.rates!!.size)
        Log.d("CDA", "rates size " + rates.size)
        rates.clear()
        val mRates = currencyData.rates
        val baseCurrencyItem = CurrencyItemData(currencyData.base!!, currencyData.rates!![currencyData.base!!]!!)
        val newRates = ArrayList(mRates!!.map { CurrencyItemData(it.key, it.value) })
        for (item in newRates) {
            if (item.name == currencyData.base) {
                newRates.remove(item)
                break
            }
        }
        rates.add(baseCurrencyItem)
        rates.addAll(newRates)
        Log.d("CDA", "after rates size " + rates.size)
    }

    fun recalculate(baseCurrencyAmount: BigDecimal) {
        /*rates.clear()
        rates.addAll(ArrayList(currencyData.rates!!.map { CurrencyItemData(it.key, it.value * baseCurrencyAmount) }))*/
        val mapRates = ratesToMutableMap(rates, baseCurrencyAmount)
        val newCurrencyData = CurrencyData(currencyData.base, mapRates)
        //updateData(newCurrencyData)
    }

    private fun ratesToMutableMap(rates: LinkedList<CurrencyItemData>, baseCurrencyAmount: BigDecimal) : MutableMap<String, BigDecimal> {
        val map = mutableMapOf<String, BigDecimal>()
        rates.map { map[it.name] = it.rate * baseCurrencyAmount }
        return map
    }

    private fun getItemIndex(currencyItemData: CurrencyItemData) : Int {
        var i = 0
        while (i < rates.size) {
            if (rates[i].name == currencyItemData.name) {
                return i
            }
            i++
        }
        return 0
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(currencyItemData: CurrencyItemData) {
            itemView.currencyName.text = currencyItemData.name
            itemView.currencyRate.setText((currencyItemData.rate * baseCurrencyAmount).toEngineeringString())

            itemView.currencyRate.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //recalculate(BigDecimal(p0.toString()))
                }

            })

            itemView.setOnClickListener {
                Log.d("CDA", "onItemClicked " + currencyItemData.name)
                listener.onItemClicked(currencyItemData)
            }
        }

    }
}

interface RecyclerAdapterCallback {
    fun onItemClicked(currencyItemData: CurrencyItemData)
}