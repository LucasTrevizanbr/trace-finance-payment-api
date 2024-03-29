package com.payment.domain.service.wallet

import com.payment.application.exception.error.Errors
import com.payment.application.exception.error.NotFoundException
import com.payment.domain.model.WalletModel
import com.payment.domain.repository.WalletRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*
import javax.transaction.Transactional

@Service
class CrudWalletService(
    val walletRepository: WalletRepository,

) {

    @Transactional
    fun save(wallet: WalletModel): WalletModel {
        return walletRepository.save(wallet)
    }

    fun findById(walletId: UUID) : WalletModel{
        return walletRepository.findById(walletId).orElseThrow {
            NotFoundException(Errors.TP101.message.format(walletId), Errors.TP101.code)
        }
    }

    fun resetWalletsLimits(firstLimitOfTheDay: BigDecimal) {
        return walletRepository.resetWalletsLimits(firstLimitOfTheDay);
    }

}
