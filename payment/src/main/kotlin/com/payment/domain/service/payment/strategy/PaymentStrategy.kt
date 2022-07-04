package com.payment.domain.service.payment.strategy

import java.math.BigDecimal


interface PaymentStrategy {

    fun getCurrentLimit () : BigDecimal

    fun walletStillHavePeriodLimit(totalPaymentsAlreadyMade: BigDecimal) : Boolean

}