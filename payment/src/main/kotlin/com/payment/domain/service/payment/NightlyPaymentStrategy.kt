package com.payment.domain.service.payment

import com.payment.application.enums.PeriodLimitValue
import java.math.BigDecimal

class NightlyPaymentStrategy: PaymentStrategy {

    override fun getCurrentLimit(): BigDecimal {
        return PeriodLimitValue.NIGHTLY_LIMIT.getLimit()
    }

    override fun walletStillHavePeriodLimit(totalPaymentsAlreadyMade: BigDecimal): Boolean {
        return totalPaymentsAlreadyMade <= getCurrentLimit()
    }
}