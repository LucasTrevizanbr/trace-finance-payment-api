package com.payment.application.enum

enum class Errors (
    val code:String,
    val message: String
) {

    TP101("TP-001", "Wallet with id: [%s] doesn't found")
}