package com.payment.domain.service.payment

import com.payment.application.enums.Period
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

interface PaymentStrategy {

    fun getLimit() : BigDecimal

    fun getTotalPaymentsUntilNow(walletId: UUID, today: LocalDate, period : Period ) : BigDecimal
}