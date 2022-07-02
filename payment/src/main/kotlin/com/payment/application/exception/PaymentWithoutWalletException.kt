package com.payment.application.exception

class PaymentWithoutWalletException(
    override val message: String,
    val errorCode: String
) : Exception()