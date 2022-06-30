package com.payment.domain.service.payment

import java.math.BigDecimal

class WeekendLimit : LimitStrategy {
    override fun getLimit(): BigDecimal {
        return BigDecimal("1000.00")
    }
}