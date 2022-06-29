package com.payment.application.extension

import com.payment.application.controller.request.MakePaymentRequest
import com.payment.application.controller.request.PostWalletRequest
import com.payment.application.controller.response.WalletLimitResponseDTO
import com.payment.application.controller.response.CreateWalletResponseDTO
import com.payment.application.enums.Period
import com.payment.domain.model.DiaryPaymentModel
import com.payment.domain.model.WalletModel
import java.time.ZoneId
import java.time.ZonedDateTime

fun PostWalletRequest.toWalletModel(): WalletModel{
    return WalletModel(
        id = null,
        ownerName = this.ownerName,
        dailyPayments = null
    )
}

fun MakePaymentRequest.toDailyPaymentModel() : DiaryPaymentModel{

    fun getPeriod() : Period {
        val zoned : ZonedDateTime = ZonedDateTime.of(this.Date, ZoneId.of("America/Sao_Paulo"));
        if(zoned.hour in 6..17){
            return Period.DAYTIME
        }
        return Period.NIGHTLY

    }

    return DiaryPaymentModel(
        id = null,
        amount = this.amount,
        period = getPeriod(),
        paymentDateTime = this.Date,
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