package com.payment.domain.service.wallet

import com.payment.domain.repository.WalletRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import java.math.BigDecimal

@ExtendWith(MockKExtension::class)
class CrudWalletServiceTest{

    @MockK
    private lateinit var walletRepository: WalletRepository


    @InjectMockKs
    private lateinit var crudWalletService: CrudWalletService

    @Test
    fun `should call a reset wallets limit routine in repository`(){
        every { walletRepository.resetWalletsLimits(BigDecimal.ONE) }.returns(Unit)

        crudWalletService.resetWalletsLimits(BigDecimal.ONE)

        verify (exactly = 1) { walletRepository.resetWalletsLimits(BigDecimal.ONE) }
    }

}