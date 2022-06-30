package com.payment.domain.service.payment

import java.math.BigDecimal

class NightlyLimit : LimitStrategy {
    override fun getLimit(): BigDecimal {
        return BigDecimal("1000.00")
    }
}