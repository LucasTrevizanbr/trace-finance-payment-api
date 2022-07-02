package com.payment.domain.service.payment

import com.payment.application.enums.PeriodLimitValue
import java.math.BigDecimal

class WeekendPaymentStrategy : PaymentStrategy {

    override fun getCurrentLimit(): BigDecimal {
        return PeriodLimitValue.WEEKEND_LIMIT.getLimit();
    }

    override fun walletStillHavePeriodLimit(totalPaymentsAlreadyMade: BigDecimal): Boolean {
        return totalPaymentsAlreadyMade < getCurrentLimit()
    }

}