package com.azati1.currencyconverter.data

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable
import java.math.BigDecimal

data class CurrencyResponse (
    @SerializedName("base")
    val base: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("rates")
    val rates: Map<String, BigDecimal>
)