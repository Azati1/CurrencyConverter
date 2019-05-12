package com.azati1.currencyconverter.model

import java.math.BigDecimal

data class CurrencyModel(
    var base: String,
    var rates: Map<String, BigDecimal>
)