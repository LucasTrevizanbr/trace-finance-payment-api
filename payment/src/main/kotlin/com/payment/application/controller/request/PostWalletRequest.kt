package com.payment.application.controller.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class PostWalletRequest(
    @field:NotEmpty(message = "Wallet must have a valid owner name, can't be null")
    @field:NotBlank(message = "Wallet must have a valid owner name, can't be empty")
    var ownerName: String
)