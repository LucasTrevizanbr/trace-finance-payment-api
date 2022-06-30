package com.payment.domain.service.payment

import java.math.BigDecimal

interface LimitStrategy {

    fun getLimit() : BigDecimal
}