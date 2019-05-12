package com.azati1.currencyconverter.view

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.azati1.currencyconverter.CurrencyItemData
import com.azati1.currencyconverter.data.CurrencyData
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList
import android.widget.TextView
import com.azati1.currencyconverter.R


class RecyclerAdapter(
    private var currencyData: CurrencyData,
    private var baseCurrencyAmount: BigDecimal,
    private val listener : RecyclerAdapterCallback
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var rates : LinkedList<RecyclerItem> =
        LinkedList(currencyData.rates.map { RecyclerItem(CurrencyItemData(it.key, it.value), it.value * baseCurrencyAmount) })

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.currency_item_view, viewGroup, false)
        return RecyclerAdapter(currencyData, baseCurrencyAmount, listener).ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.name.text = rates[i].currencyItemData.name
        holder.rate.setText((rates[i].amountedRate).toEngineeringString())
        holder.rate.isEnabled = false
        holder.itemView.setOnClickListener {
            Log.d("CDA", "onItemClicked " + rates[i].currencyItemData.name)
            //currencyData.base = rates[i].currencyItemData.name
            listener.onItemClicked(rates[i].currencyItemData)
        }
    }

    override fun getItemCount(): Int {
        return rates.size
    }

    fun updateData(currencyData: CurrencyData) {
        rates.clear()
        val mRates = currencyData.rates
        val newRates = ArrayList(mRates.map { RecyclerItem(CurrencyItemData(it.key, it.value),it.value * baseCurrencyAmount) })
        for (item in newRates) {
            if (item.currencyItemData.name == currencyData.base) {
                newRates.remove(item)
                break
            }
        }
        newRates.forEach{Log.d("CDA", it.currencyItemData.name + " " + it.currencyItemData.rate + " " + it.amountedRate)}
        rates.addAll(newRates)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.currencyName)
        val rate = itemView.findViewById<EditText>(R.id.currencyRate)
    }

    fun clearData() {
        rates.clear()
    }

    fun recalculateCurrencies(baseCurrencyAmount: BigDecimal) {
        this.baseCurrencyAmount = baseCurrencyAmount
        updateData(currencyData)
    }

}

class RecyclerItem(
    val currencyItemData: CurrencyItemData,
    val amountedRate: BigDecimal
)

interface RecyclerAdapterCallback {
    fun onItemClicked(currencyItemData: CurrencyItemData)
}