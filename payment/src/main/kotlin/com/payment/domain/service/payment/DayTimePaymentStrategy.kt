package com.payment.domain.service.payment

import com.payment.application.enums.PeriodLimitValue
import java.math.BigDecimal

class DayTimePaymentStrategy : PaymentStrategy {

    override fun getCurrentLimit(): BigDecimal {
        return PeriodLimitValue.DAYTIME_LIMIT.getLimit()
    }

    override fun walletStillHavePeriodLimit(totalPaymentsAlreadyMade: BigDecimal): Boolean {
        return totalPaymentsAlreadyMade < getCurrentLimit()
    }
}