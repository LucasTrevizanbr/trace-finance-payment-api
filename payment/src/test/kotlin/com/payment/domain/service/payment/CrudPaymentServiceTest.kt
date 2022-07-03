package com.payment.domain.service.payment

import com.payment.application.enums.Period
import com.payment.domain.model.PaymentModel
import com.payment.domain.model.WalletModel
import com.payment.domain.repository.PaymentRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

@ExtendWith(MockKExtension::class)
class CrudPaymentServiceTest{

    @MockK
    private lateinit var paymentRepository: PaymentRepository

    @InjectMockKs
    private lateinit var crudPaymentService: CrudPaymentService


    @Nested
    inner class `Test get total payment by period rules`{

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
        ownerName: String = "Owner name",
        limitValue : BigDecimal = BigDecimal("5000.00"),
        payments : MutableList<PaymentModel> = ArrayList()
    ) : WalletModel {
        return WalletModel(id, ownerName, limitValue, payments)
    }
}