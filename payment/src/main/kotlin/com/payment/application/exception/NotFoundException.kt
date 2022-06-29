package com.payment.application.exception

class NotFoundException(
    override val message: String,
    val errorCode: String
) : Exception() {
}