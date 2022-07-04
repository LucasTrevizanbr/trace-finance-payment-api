package com.payment.application.exception.error

class WalletLimitReachedException (
    override val message: String,
    val errorCode : String
) : Exception()