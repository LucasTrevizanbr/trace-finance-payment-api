package com.payment.application.controller.response

import java.math.BigDecimal
import java.util.*

data class WalletResponseDTO(
    val id: UUID?,
    val ownerName: String
)
