package com.payment.domain.service

import com.payment.application.controller.request.MakePaymentRequest
import com.payment.application.enums.Errors
import com.payment.application.enums.Period
import com.payment.application.exception.NotFoundException
import com.payment.domain.model.WalletModel
import com.payment.domain.repository.DiaryPaymentRepository
import com.payment.domain.repository.WalletRepository
import org.springframework.stereotype.Service
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import javax.transaction.Transactional

@Service
class WalletService(
    val walletRepository: WalletRepository,
    val diaryPaymentRepository: DiaryPaymentRepository
) {

    @Transactional
    fun save(wallet: WalletModel): WalletModel {
        return walletRepository.save(wallet)
    }

    fun getLimitsById(walletId: UUID): WalletModel {
        return walletRepository.findById(walletId).orElseThrow {
            NotFoundException(Errors.TP101.message.format(walletId), Errors.TP101.code)
        }
    }

    @Transactional
    fun makePayment(walletId: UUID, request: MakePaymentRequest) {
        walletRepository.findById(walletId).orElseThrow {
            NotFoundException(Errors.TP101.message.format(walletId), Errors.TP101.code)
        }

        val dateTimeNow = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));

        val currentPeriod = when (dateTimeNow.hour) {
            in 6 .. 17 -> Period.DAYTIME
            else -> { Period.NIGHTLY}
        }

        val isWeekend = when (dateTimeNow.dayOfWeek.value){
            in 2 ..6 -> false;
            else -> {true}
        }

        if(isWeekend){
            //diaryPaymentRepository.hasLimitInWeekend(dateTimeNow.toLocalDate())
        }

        //diaryPaymentRepository.paymentsTotalAmountTodayByPeriod(currentPeriod, dateTimeNow.toLocalDate());



    }
}
