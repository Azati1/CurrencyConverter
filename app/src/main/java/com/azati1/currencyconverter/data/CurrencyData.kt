package com.azati1.currencyconverter.data

import java.math.BigDecimal

data class CurrencyData(
    var base: String,
    var rates: Map<String, BigDecimal>
)