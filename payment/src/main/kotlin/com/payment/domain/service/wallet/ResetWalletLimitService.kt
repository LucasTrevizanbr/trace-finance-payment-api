package com.payment.domain.service.wallet

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ResetWalletLimitService(
    private val FIRST_LIMIT_OF_THE_DAY: BigDecimal = BigDecimal("5000.00"),
    private val crudWalletService: CrudWalletService
) {

    @Scheduled(cron = "0 0 0 * * *", zone = "America/Sao_Paulo")
    fun resetWalletsLimits(){
        crudWalletService.resetWalletsLimits(FIRST_LIMIT_OF_THE_DAY)
    }
}

