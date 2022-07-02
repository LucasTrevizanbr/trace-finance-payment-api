package com.payment.domain.service.payment

import com.payment.application.enums.Period
import com.payment.application.exception.PaymentWithoutWalletException
import com.payment.domain.model.PaymentModel
import com.payment.domain.model.WalletModel
import com.payment.domain.repository.PaymentRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockKExtension::class)
class CrudPaymentServiceTest{

    @MockK
    private lateinit var paymentRepository: PaymentRepository

    @InjectMockKs
    private lateinit var crudPaymentService: CrudPaymentService

    @Test
    fun `should  save a payment`(){
        val fakeWallet = buildFakeWallet();
        val fakePayment = buildFakePayment(wallet = fakeWallet);

        every { paymentRepository.save(fakePayment) } returns fakePayment

        val payment =  crudPaymentService.save(fakePayment)

        assertEquals(fakePayment, payment)
        verify (exactly = 1) { paymentRepository.save(fakePayment) }
    }

    @Test
    fun `should throw a exception when trying to save a payment without wallet`(){
        val fakePayment = buildFakePayment();

        val error = assertThrows<PaymentWithoutWalletException> { crudPaymentService.save(fakePayment) }

        assertEquals("The payment doesn't have a wallet linked", error.message)
        assertEquals("TP-205", error.errorCode)
        verify (exactly = 0) { paymentRepository.save(fakePayment) }
    }

    @Test
    fun `should return the total of payments from a wallet on the day on a certain period `(){
        val fakeTotal = BigDecimal("3000")
        val fakeWalletId = UUID.randomUUID()
        val fakeDate = LocalDate.now()
        val fakePeriod = Period.DAYTIME

        every { paymentRepository.totalPaymentsUntilNowByPeriod(fakeWalletId, fakeDate, fakePeriod ) } returns fakeTotal

        val amount = crudPaymentService.getTotalPaymentsByPeriod(fakeWalletId, fakeDate, fakePeriod)

        assertEquals(fakeTotal, amount)
        verify (exactly = 1) { paymentRepository.totalPaymentsUntilNowByPeriod(fakeWalletId, fakeDate, fakePeriod) }
    }

    @Test
    fun `should return 0 if total payments is null result `(){

        val fakeWalletId = UUID.randomUUID()
        val fakeDate = LocalDate.now()
        val fakePeriod = Period.DAYTIME

        every { paymentRepository.totalPaymentsUntilNowByPeriod(fakeWalletId, fakeDate, fakePeriod) } returns null

        val amountPeriod = crudPaymentService.getTotalPaymentsByPeriod(fakeWalletId, fakeDate, fakePeriod)

        assertEquals(amountPeriod, BigDecimal("0"))
        verify (exactly = 1) { paymentRepository.totalPaymentsUntilNowByPeriod(fakeWalletId, fakeDate, fakePeriod) }
    }

    private fun buildFakePayment (
        id : Long? = 1L,
        period:Period  = Period.DAYTIME,
        amount: BigDecimal = BigDecimal("1000.00"),
        paymentDateTime: LocalDateTime = LocalDateTime.now(),
        wallet: WalletModel? = null
    ): PaymentModel {
        return PaymentModel(id, period, amount, paymentDateTime, wallet)
    }

    private fun buildFakeWallet(
        id : UUID = UUID.randomUUID(),
        owenerName: String = "Owner name",
        limitValue : BigDecimal = BigDecimal("5000.00"),
        payments : List<PaymentModel>? = null
    ) : WalletModel {
        return WalletModel(id, owenerName, limitValue, payments)
    }
}