package com.payment.application.exception

class WalletLimitReachedException (
    override val message: String,
    val errorCode : String
) : Exception()