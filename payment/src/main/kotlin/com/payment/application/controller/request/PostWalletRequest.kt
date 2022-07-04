package com.payment.application.controller.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class PostWalletRequest(
    @field:NotEmpty(message = "Wallet must have a valid owner name, can't be null")
    @field:NotBlank(message = "Wallet must have a valid owner name, can't be empty")
    @field:Size(min = 2, max = 200)
    var ownerName: String
)