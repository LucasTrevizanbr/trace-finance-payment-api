package com.payment.application.enum

enum class Errors (
    val code:String,
    val message: String
) {

    TP001("TP001","Invalid Request"),
    TP101("TP-101", "Wallet with id: [%s] doesn't found"),
    TP202("TP-102", "Invalid Date format for transaction, please send a Date in ISO-8016 on UTC without " +
            "OffSet, for example: [yyyy-MM-ddTHH:mm:ssZ]" )
}