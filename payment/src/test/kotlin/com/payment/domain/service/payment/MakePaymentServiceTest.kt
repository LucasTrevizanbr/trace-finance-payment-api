package com.payment.domain.service.payment

import com.payment.application.enums.Period
import com.payment.application.enums.PeriodLimitValue
import com.payment.application.exception.PeriodLimitReachedException
import com.payment.application.exception.WalletLimitReachedException
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

    private var nightlyLimit = PeriodLimitValue.NIGHTLY_LIMIT.getLimit();
    private var daytimeLimit = PeriodLimitValue.DAYTIME_LIMIT.getLimit();
    private var weekendLimit = PeriodLimitValue.WEEKEND_LIMIT.getLimit();

    @Test
    fun `Should successfully do a payment in daytime period`(){

        val fakePaymentDaytime = buildFakePayment(period = Period.DAYTIME, amount = BigDecimal("1000"))
        val fakeWalletWithLimit = buildFakeWallet(limitValue = BigDecimal("1000.00"))

        every {
            crudPaymentService.getTotalPaymentsByPeriod( fakeWalletWithLimit.id,
                fakePaymentDaytime.paymentDateTime.toLocalDate(), fakePaymentDaytime.period)
        } returns daytimeLimit.minus(BigDecimal("0000.01"))

        mocksEveryReturnOfRepositorys(fakeWalletWithLimit, fakePaymentDaytime)

        makePaymentService.makePayment(fakeWalletWithLimit, fakePaymentDaytime)

        checkFunCallsInsCaseOfSuccess(fakeWalletWithLimit, fakePaymentDaytime)
    }

    @Test
    fun `Should successfully do a payment in nightly period`(){

        val fakePaymentDaytime = buildFakePayment(period = Period.NIGHTLY, amount = BigDecimal("1000"))
        val fakeWalletWithLimit = buildFakeWallet(limitValue = BigDecimal("1000.00"))

        every {
            crudPaymentService.getTotalPaymentsByPeriod( fakeWalletWithLimit.id,
                fakePaymentDaytime.paymentDateTime.toLocalDate(), fakePaymentDaytime.period)
        } returns nightlyLimit.minus(BigDecimal("0000.01"))

        mocksEveryReturnOfRepositorys(fakeWalletWithLimit, fakePaymentDaytime)

        makePaymentService.makePayment(fakeWalletWithLimit, fakePaymentDaytime)

        checkFunCallsInsCaseOfSuccess(fakeWalletWithLimit, fakePaymentDaytime)
    }

    @Test
    fun `Should successfully do a payment in weekend period`(){

        val fakeWeekendPayment = buildFakePayment(period = Period.WEEKEND, amount = BigDecimal("1000"))
        val fakeWalletWithLimit = buildFakeWallet(limitValue = BigDecimal("1000.00"))

        every {
            crudPaymentService.getTotalPaymentsByPeriod(
                fakeWalletWithLimit.id,
                fakeWeekendPayment.paymentDateTime.toLocalDate(), fakeWeekendPayment.period
            )
        } returns weekendLimit.minus(BigDecimal("0000.01"))

        mocksEveryReturnOfRepositorys(fakeWalletWithLimit, fakeWeekendPayment)

        makePaymentService.makePayment(fakeWalletWithLimit, fakeWeekendPayment)

        checkFunCallsInsCaseOfSuccess(fakeWalletWithLimit, fakeWeekendPayment)
    }


    @Test
    fun `Should throw a exception when wallet doesn't have more daytime limit`(){

        val fakePaymentDaytime = buildFakePayment(period = Period.DAYTIME, amount = BigDecimal("1000.00"))
        val fakeWalletWithLimit = buildFakeWallet(limitValue = BigDecimal("1000.00"))

        every {
            crudPaymentService.getTotalPaymentsByPeriod( fakeWalletWithLimit.id,
                fakePaymentDaytime.paymentDateTime.toLocalDate(), fakePaymentDaytime.period)
        } returns daytimeLimit
        mocksEveryReturnOfRepositorys(fakeWalletWithLimit, fakePaymentDaytime)

        val error = assertThrows<PeriodLimitReachedException>{
            makePaymentService.makePayment(fakeWalletWithLimit, fakePaymentDaytime)
        }

        assertEquals("The DAYTIME limit of R$4000.00 is already been reached", error.message)
        assertEquals("TP-203", error.errorCode)
        checkFunCallsInCaseOfError(fakeWalletWithLimit, fakePaymentDaytime)
    }

    @Test
    fun `Should throw a exception when wallet doesn't have more nightly limit`(){

        val fakeNightlyPayment = buildFakePayment(period = Period.NIGHTLY, amount = BigDecimal("1000.00"))
        val fakeWalletWithLimit = buildFakeWallet(limitValue = BigDecimal("1000.00"))

        every {
            crudPaymentService.getTotalPaymentsByPeriod( fakeWalletWithLimit.id,
                fakeNightlyPayment.paymentDateTime.toLocalDate(), fakeNightlyPayment.period)
        } returns nightlyLimit
        mocksEveryReturnOfRepositorys(fakeWalletWithLimit, fakeNightlyPayment)


        val error = assertThrows<PeriodLimitReachedException>{
            makePaymentService.makePayment(fakeWalletWithLimit, fakeNightlyPayment)
        }

        assertEquals("The NIGHTLY limit of R$1000.00 is already been reached", error.message)
        assertEquals("TP-203", error.errorCode)
        checkFunCallsInCaseOfError(fakeWalletWithLimit, fakeNightlyPayment)
    }

    @Test
    fun `Should throw a exception when wallet doesn't have more weekend limit`(){

        val fakeWeekendPayment = buildFakePayment(period = Period.WEEKEND, amount = BigDecimal("1000.00"))
        val fakeWalletWithLimit = buildFakeWallet(limitValue = BigDecimal("1000.00"))

        every {
            crudPaymentService.getTotalPaymentsByPeriod( fakeWalletWithLimit.id,
                fakeWeekendPayment.paymentDateTime.toLocalDate(), fakeWeekendPayment.period)
        } returns weekendLimit

        mocksEveryReturnOfRepositorys(fakeWalletWithLimit, fakeWeekendPayment)

        val error = assertThrows<PeriodLimitReachedException>{
            makePaymentService.makePayment(fakeWalletWithLimit, fakeWeekendPayment)
        }

        assertEquals("The WEEKEND limit of R$1000.00 is already been reached", error.message)
        assertEquals("TP-203", error.errorCode)
        checkFunCallsInCaseOfError(fakeWalletWithLimit, fakeWeekendPayment)
    }

    @Test
    fun `Should throw a exception when wallet doesn't have limit at all`(){

        val fakePaymentDaytime = buildFakePayment(period = Period.DAYTIME, amount = BigDecimal("1000.00"))
        val fakeWalletWithNoLimit = buildFakeWallet(limitValue = BigDecimal("999.00"))

        every {
            crudPaymentService.getTotalPaymentsByPeriod( fakeWalletWithNoLimit.id,
                fakePaymentDaytime.paymentDateTime.toLocalDate(), fakePaymentDaytime.period)
        } returns daytimeLimit.minus(BigDecimal("0000.01"))
        mocksEveryReturnOfRepositorys(fakeWalletWithNoLimit, fakePaymentDaytime)

        val error = assertThrows<WalletLimitReachedException>{
            makePaymentService.makePayment(fakeWalletWithNoLimit, fakePaymentDaytime)
        }

        assertEquals("The wallet has no limit for this payment", error.message)
        assertEquals("TP-204", error.errorCode)
        checkFunCallsInCaseOfError(fakeWalletWithNoLimit, fakePaymentDaytime)
    }


    private fun mocksEveryReturnOfRepositorys(
        fakeWalletWithLimit: WalletModel,
        fakePaymentDaytime: PaymentModel
    ) {
        every { crudWalletService.save(fakeWalletWithLimit) } returns fakeWalletWithLimit
        every { crudPaymentService.save(fakePaymentDaytime) } returns fakePaymentDaytime
    }

    private fun checkFunCallsInsCaseOfSuccess(
        fakeWalletWithLimit: WalletModel,
        fakePayment: PaymentModel
    ) {
        verify(exactly = 1) {
            crudPaymentService.getTotalPaymentsByPeriod(
                fakeWalletWithLimit.id,
                fakePayment.paymentDateTime.toLocalDate(), fakePayment.period
            )
        }
        verify(exactly = 1) { crudWalletService.save(fakeWalletWithLimit) }
        verify(exactly = 1) { crudPaymentService.save(fakePayment) }
    }

    private fun checkFunCallsInCaseOfError(
        fakeWalletWithLimit: WalletModel,
        fakePayment: PaymentModel
    ) {
        verify(exactly = 1) {
            crudPaymentService.getTotalPaymentsByPeriod(
                fakeWalletWithLimit.id,
                fakePayment.paymentDateTime.toLocalDate(), fakePayment.period
            )
        }
        verify(exactly = 0) { crudWalletService.save(fakeWalletWithLimit) }
        verify(exactly = 0) { crudPaymentService.save(fakePayment) }
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