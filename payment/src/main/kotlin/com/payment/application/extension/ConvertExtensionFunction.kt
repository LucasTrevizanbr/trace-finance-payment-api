package com.payment.application.extension

import com.payment.application.controller.request.MakePaymentRequest
import com.payment.application.controller.request.PostWalletRequest
import com.payment.application.controller.response.WalletLimitResponseDTO
import com.payment.application.controller.response.CreateWalletResponseDTO
import com.payment.application.enums.Period
import com.payment.domain.model.PaymentModel
import com.payment.domain.model.WalletModel
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

fun PostWalletRequest.toWalletModel(): WalletModel{
    return WalletModel(
        ownerName = this.ownerName,
        payments = null
    )
}

fun MakePaymentRequest.toPaymentModel() : PaymentModel{

    val currentDateTime : ZonedDateTime = ZonedDateTime.of(this.date, LocalTime.now(), ZoneId.of("America/Sao_Paulo") );

    fun getPeriod() : Period {
        if(currentDateTime.hour in 6..17){
            return Period.DAYTIME
        }
        return Period.NIGHTLY
    }

    return PaymentModel(
        id = null,
        amount = this.amount,
        period = getPeriod(),
        paymentDateTime = currentDateTime.toLocalDateTime(),
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