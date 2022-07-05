package com.payment.domain.service.wallet

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ResetWalletLimitService(
    private val FIRST_LIMIT_OF_THE_DAY: BigDecimal = BigDecimal("5.000"),
    private val crudWalletService: CrudWalletService
) {

    companion object{
        private const val  CRON_DAILY_RESET : String = "0 0 0 1/1 * ? *"
    }

    @Scheduled(cron = CRON_DAILY_RESET, zone = "America/Sao_Paulo")
    fun resetWalletsLimits(){
        crudWalletService.resetWalletsLimits(FIRST_LIMIT_OF_THE_DAY)
    }
}

