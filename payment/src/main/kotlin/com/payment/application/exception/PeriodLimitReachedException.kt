package com.payment.application.exception

class PeriodLimitReachedException(
    override val message: String,
    val errorCode : String
) : Exception()