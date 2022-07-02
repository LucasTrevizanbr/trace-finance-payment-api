package com.payment.domain.service.payment

import com.payment.application.enums.Period
import com.payment.application.exception.PeriodLimitReachedException
import com.payment.domain.model.PaymentModel
import com.payment.domain.model.WalletModel
import com.payment.domain.service.wallet.CrudWalletService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockKExtension::class)
class MakePaymentServiceTest {

    @MockK
    private lateinit var  crudPaymentService: CrudPaymentService

    @MockK
    private lateinit var crudWalletService: CrudWalletService

    @InjectMockKs
    private lateinit var makePaymentService: MakePaymentService

    @Test
    fun `Should successfully do a payment in daytime period`(){

        val fakePaymentDaytime = buildFakePayment(period = Period.DAYTIME)
        val fakeWalletWithLimit = buildFakeWallet(limitValue = BigDecimal("999.99"))

        every {
            crudPaymentService.getTotalPaymentsByPeriod( fakeWalletWithLimit.id,
                fakePaymentDaytime.paymentDateTime.toLocalDate(), fakePaymentDaytime.period)
        } returns BigDecimal("3999.99")

        every { crudWalletService.save(fakeWalletWithLimit) } returns fakeWalletWithLimit
        every { crudPaymentService.save(fakePaymentDaytime) } returns fakePaymentDaytime

        makePaymentService.makePayment(fakeWalletWithLimit, fakePaymentDaytime)

        verify (exactly = 1) {  crudPaymentService.getTotalPaymentsByPeriod( fakeWalletWithLimit.id,
            fakePaymentDaytime.paymentDateTime.toLocalDate(), fakePaymentDaytime.period) }
        verify (exactly = 1) {  crudWalletService.save(fakeWalletWithLimit) }
        verify (exactly = 1) {  crudPaymentService.save(fakePaymentDaytime) }
    }

    @Test
    fun `Should throw a exception when wallet doesn't have more daytime limit`(){

        val fakePaymentDaytime = buildFakePayment(period = Period.DAYTIME)
        val fakeWalletWithLimit = buildFakeWallet(limitValue = BigDecimal("1000.00"))

        every {
            crudPaymentService.getTotalPaymentsByPeriod( fakeWalletWithLimit.id,
                fakePaymentDaytime.paymentDateTime.toLocalDate(), fakePaymentDaytime.period)
        } returns BigDecimal("4000.00")
        every { crudWalletService.save(fakeWalletWithLimit) } returns fakeWalletWithLimit
        every { crudPaymentService.save(fakePaymentDaytime) } returns fakePaymentDaytime

        val error = assertThrows<PeriodLimitReachedException>{
            makePaymentService.makePayment(fakeWalletWithLimit, fakePaymentDaytime)
        }

        assertEquals("The DAYTIME limit of R$4000.00 is already been reached", error.message)
        assertEquals("TP-203", error.errorCode)
        verify (exactly = 1) {  crudPaymentService.getTotalPaymentsByPeriod( fakeWalletWithLimit.id,
            fakePaymentDaytime.paymentDateTime.toLocalDate(), fakePaymentDaytime.period) }
        verify (exactly = 0) {  crudWalletService.save(fakeWalletWithLimit) }
        verify (exactly = 0) {  crudPaymentService.save(fakePaymentDaytime) }
    }

    private fun buildFakeWallet(
        id : UUID = UUID.randomUUID(),
        owenerName: String = "Owner name",
        limitValue : BigDecimal = BigDecimal("5000.00"),
        payments : List<PaymentModel>? = null
    ) : WalletModel {
        return WalletModel(id, owenerName, limitValue, payments)
    }

    private fun buildFakePayment (
        id : Long = 1L,
        period: Period = Period.DAYTIME,
        amount: BigDecimal = BigDecimal("1000.00"),
        paymentDateTime: LocalDateTime = LocalDateTime.now(),
        wallet: WalletModel? = null
    ): PaymentModel {
        return PaymentModel(id, period, amount, paymentDateTime, wallet)
    }
}