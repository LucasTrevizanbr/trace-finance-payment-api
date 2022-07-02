package com.payment.application.enums

import java.math.BigDecimal

enum class PeriodLimitValue {

    DAYTIME_LIMIT {
        override fun getLimit(): BigDecimal = BigDecimal("4000.00")
    },
    NIGHTLY_LIMIT{
        override fun getLimit(): BigDecimal = BigDecimal("1000.00")
    },
    WEEKEND_LIMIT {
        override fun getLimit(): BigDecimal = BigDecimal("1000.00")
    };

    abstract fun getLimit() : BigDecimal
}