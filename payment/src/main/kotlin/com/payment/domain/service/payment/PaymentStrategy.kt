package com.payment.domain.service.payment

import java.math.BigDecimal


interface PaymentStrategy {

    fun getCurrentLimit () : BigDecimal

    fun walletStillHavePeriodLimit(totalPaymentsAlreadyMade: BigDecimal) : Boolean

}