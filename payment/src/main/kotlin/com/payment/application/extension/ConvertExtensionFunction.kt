package com.payment.application.extension

import com.payment.application.controller.request.PostWalletRequest
import com.payment.application.controller.response.WalletLimitResponseDTO
import com.payment.application.controller.response.WalletResponseDTO
import com.payment.domain.model.WalletModel

fun PostWalletRequest.toWalletModel(): WalletModel{
    return WalletModel(
        id = null,
        ownerName = this.ownerName
    )
}
fun WalletModel.toResponse(): WalletResponseDTO {
    return WalletResponseDTO(
        id = this.id,
        ownerName = this.ownerName,
    )
}

fun WalletModel.toResponseLimit() : WalletLimitResponseDTO{
    return WalletLimitResponseDTO(
        value = this.limit_value
    )
}