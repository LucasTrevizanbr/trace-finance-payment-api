package com.payment.domain.service.wallet

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal

@ExtendWith(MockKExtension::class)
class ResetWalletLimitServiceTest{

    @MockK
    private lateinit var  crudWalletService: CrudWalletService

    @MockK
    private var FIRST_LIMIT_OF_THE_DAY: BigDecimal = BigDecimal("5.000")

    @InjectMockKs
    private lateinit var resetWalletLimitService: ResetWalletLimitService

    @Test
    fun `should call a reset wallets limit routine in walletService`(){
        every { crudWalletService.resetWalletsLimits(FIRST_LIMIT_OF_THE_DAY) }.returns(Unit)

        resetWalletLimitService.resetWalletsLimits()

        verify (exactly = 1) { crudWalletService.resetWalletsLimits(FIRST_LIMIT_OF_THE_DAY) }

    }
}