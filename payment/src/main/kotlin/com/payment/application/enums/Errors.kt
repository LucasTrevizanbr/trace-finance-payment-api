package com.payment.application.enums

enum class Errors (
    val code:String,
    val message: String
) {

    TP001("TP001","Invalid Request"),

    TP101("TP-101", "Wallet with id: [%s] doesn't found"),

    TP202("TP-202", "Invalid Date format for transaction, please send only a Date in ISO-8016, for example: [yyyy-MM-dd]" ),
    TP203("TP-203", "The %s limit of R$%s is already been reached"),
    TP204("TP-204", "The wallet has no more limit, limit: R$%s"),
    TP205("TP-205", "The payment doesn't have a wallet linked")
}