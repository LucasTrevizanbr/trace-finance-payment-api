package com.payment.domain.service.payment

import com.payment.application.enums.Period
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

class NightlyPaymentStrategy(
    private val crudPaymentService: CrudPaymentService
) : PaymentStrategy {
    override fun getLimit(): BigDecimal {
        return BigDecimal("1000.00")
    }

    override fun getTotalPaymentsUntilNow(walletId: UUID, today: LocalDate, period: Period): BigDecimal {
        return crudPaymentService.totalPaymentsByPeriod(walletId, today, period)
    }
}