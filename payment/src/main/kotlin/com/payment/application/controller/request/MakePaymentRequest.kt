package com.payment.application.controller.request

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.LocalDate
import javax.validation.constraints.Max
import javax.validation.constraints.Positive

data class MakePaymentRequest(

    @field:Positive(message = "The amount value needs to be positive")
    @field:Max(value = 1000, message = "Payment limit is R$1000.00 for transaction")
    val amount: BigDecimal,

    @field:JsonFormat(pattern = "yyyy-MM-dd")
    val date : LocalDate
)