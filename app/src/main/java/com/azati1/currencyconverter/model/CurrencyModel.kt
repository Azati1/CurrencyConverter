package com.azati1.currencyconverter.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class CurrencyModel(
    @SerializedName("base") var base: String? = null,
    @SerializedName("rates") var rates: Map<String, BigDecimal>? = null
)