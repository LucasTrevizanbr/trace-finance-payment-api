package com.payment.domain.service.payment

import com.payment.application.enums.Period
import java.math.BigDecimal

class DaytimeLimit : LimitStrategy {
    override fun getLimit(): BigDecimal {
        return BigDecimal("4000.00")
    }
}