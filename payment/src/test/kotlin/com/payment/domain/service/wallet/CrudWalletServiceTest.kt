package com.payment.domain.service.wallet

import com.payment.application.exception.NotFoundException
import com.payment.domain.model.PaymentModel
import com.payment.domain.model.WalletModel
import com.payment.domain.repository.WalletRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.util.Assert

import java.math.BigDecimal
import java.util.*

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

    @Test
    fun `should return a wallet if exists`(){

        val fakeUUID = UUID.randomUUID()
        val fakeWalletModel = Optional.of(buildWallet())

        every  { walletRepository.findById(fakeUUID) }  returns fakeWalletModel

        val wallet = crudWalletService.findById(fakeUUID)

        assertEquals(fakeWalletModel.get(), wallet)
        verify (exactly = 1){  walletRepository.findById(fakeUUID) }
    }

    @Test
    fun `should throw exception if wallet not found `(){

        val fakeUUID = UUID.randomUUID()

        every  { walletRepository.findById(fakeUUID) }  returns Optional.empty()

        val error = assertThrows<NotFoundException> { crudWalletService.findById(fakeUUID) }

        assertEquals( "Wallet with id: [${fakeUUID}] doesn't found",error.message)
        assertEquals( "TP-101",error.errorCode)
        verify (exactly = 1){  walletRepository.findById(fakeUUID) }
    }

    @Test
    fun `should save a wallet`(){

        val fakeWallet = buildWallet()

        every  { walletRepository.save(fakeWallet) }  returns fakeWallet

        val wallet =  crudWalletService.save(fakeWallet)

        assertEquals( fakeWallet, wallet)
        verify (exactly = 1){  walletRepository.save(fakeWallet) }
    }


    private fun buildWallet(
        id : UUID = UUID.randomUUID(),
        owenerName: String = "Owner name",
        limitValue : BigDecimal = BigDecimal("5000.00"),
        payments : List<PaymentModel>? = null
    ) : WalletModel {
        return WalletModel(id, owenerName, limitValue, payments)
    }

}