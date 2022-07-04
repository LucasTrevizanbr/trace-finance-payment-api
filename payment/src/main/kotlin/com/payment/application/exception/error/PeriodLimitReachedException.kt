package com.payment.application.exception.error

class PeriodLimitReachedException(
    override val message: String,
    val errorCode : String
) : Exception()