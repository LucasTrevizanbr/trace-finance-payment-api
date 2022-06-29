package com.payment.application.controller.request

import javax.validation.constraints.NotEmpty

data class PostWalletRequest(

    @field:NotEmpty(message = "Wallet must have a valid owner name, can't be null or empty")
    val ownerName: String
)