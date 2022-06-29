package com.payment.domain.service

import com.payment.application.controller.request.MakePaymentRequest
import com.payment.application.enum.Errors
import com.payment.application.exception.NotFoundException
import com.payment.domain.model.WalletModel
import com.payment.domain.repository.WalletRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class WalletService(
    val walletRepository: WalletRepository
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


    }
}
