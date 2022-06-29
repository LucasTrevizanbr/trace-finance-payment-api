package com.payment.domain.service

import com.payment.application.enum.Errors
import com.payment.application.exception.NotFoundException
import com.payment.domain.model.WalletModel
import com.payment.domain.repository.WalletRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class WalletService(
    val walletRepository: WalletRepository
) {

    fun save(wallet: WalletModel): WalletModel {
        return walletRepository.save(wallet)
    }

    fun getLimitsById(walletId: UUID): WalletModel {
        return walletRepository.findById(walletId).orElseThrow {
            NotFoundException(Errors.TP101.message.format(walletId), Errors.TP101.code)
        }
    }
}
