package com.payment.application.exception.error

class NotFoundException(
    override val message: String,
    val errorCode: String
) : Exception()