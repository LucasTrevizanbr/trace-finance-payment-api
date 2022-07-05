package com.payment.infra.extension

import com.payment.application.controller.request.MakePaymentRequest
import com.payment.application.controller.request.PostWalletRequest
import com.payment.application.controller.response.WalletLimitResponseDTO
import com.payment.application.controller.response.CreateWalletResponseDTO
import com.payment.domain.service.period.Period
import com.payment.domain.model.PaymentModel
import com.payment.domain.model.WalletModel
import java.time.*

fun PostWalletRequest.toWalletModel(): WalletModel{
    return WalletModel(
        ownerName = this.ownerName,
    )
}

fun MakePaymentRequest.toPaymentModel(period: Period, brazilTime: LocalTime): PaymentModel{

    return PaymentModel(
        id = null,
        amount = this.amount,
        period = period,
        paymentDateTime = LocalDateTime.of(this.date, brazilTime),
        wallet = null,
    )
}


fun WalletModel.toResponse(): CreateWalletResponseDTO {
    return CreateWalletResponseDTO(
        id = this.id,
        ownerName = this.ownerName,
    )
}

fun WalletModel.toResponseLimitValue() : WalletLimitResponseDTO{
    return WalletLimitResponseDTO(
        value = this.limitValue
    )
}