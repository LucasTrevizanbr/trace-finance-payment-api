package com.payment.domain.repository

import com.payment.application.enums.Period
import com.payment.domain.model.PaymentModel
import com.payment.domain.model.WalletModel
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@ExtendWith(MockKExtension::class)
@SpringBootTest
@ActiveProfiles("dev")
class PaymentRepositoryTests {

    @Autowired
    private lateinit var paymentRepository : PaymentRepository

    @Autowired
    private lateinit var walletRepository: WalletRepository

    @BeforeEach
    fun cleanDataBase () = walletRepository.deleteAll()

    @Nested
    inner class `Test total payments by period`{

        @Test
        fun `Should return the total payments made in one day to a wallet according to a daytime period`(){
            val wallet = walletRepository.save(buildWallet())
            val dateOfPayment = LocalDateTime.of(LocalDate.of(2022,1,1), LocalTime.now())
            val paymentPeriod = Period.DAYTIME

            paymentRepository.save(buildPayment(
                    id = 1, period = paymentPeriod, wallet = wallet, amount = BigDecimal("2000.00"),
                    paymentDateTime = dateOfPayment
            ))

            paymentRepository.save( buildPayment(
                id = 2, period = paymentPeriod, wallet = wallet, amount = BigDecimal("2000.00"),
                paymentDateTime = dateOfPayment
            ))

            val expectedAmount = paymentRepository
                .totalPaymentsUntilNowByPeriod(wallet.id, dateOfPayment.toLocalDate(),paymentPeriod )

            Assertions.assertEquals(BigDecimal("4000.00"),expectedAmount)

        }

        @Test
        fun `Should return the total payments made in one day to a wallet according to a weekend period`(){
            val wallet = walletRepository.save(buildWallet())
            val dateOfPayment = LocalDateTime.of(LocalDate.of(2022,1,1), LocalTime.now())
            val paymentPeriod = Period.WEEKEND

            paymentRepository.save(buildPayment(
                    id = 1, period = paymentPeriod, wallet = wallet, amount = BigDecimal("350.00"),
                    paymentDateTime = dateOfPayment
            ))

            paymentRepository.save(buildPayment(
                    id = 2, period = paymentPeriod, wallet = wallet, amount = BigDecimal("350.00"),
                    paymentDateTime = dateOfPayment
            ))

            val expectedAmount = paymentRepository
                .totalPaymentsUntilNowByPeriod(wallet.id, dateOfPayment.toLocalDate(),paymentPeriod )

            Assertions.assertEquals(BigDecimal("700.00"),expectedAmount)

        }

        @Test
        fun `Should return the total payments made in one day to a wallet according to a nightly period`(){
            val wallet = walletRepository.save(buildWallet())
            val dateOfPayment = LocalDateTime.of(LocalDate.of(2022,1,1), LocalTime.now())
            val paymentPeriod = Period.NIGHTLY

           paymentRepository.save(buildPayment(
                    id = 1, period = paymentPeriod, wallet = wallet, amount = BigDecimal("300.00"),
                    paymentDateTime = dateOfPayment
            ))
            paymentRepository.save(buildPayment(
                    id = 2, period = paymentPeriod, wallet = wallet, amount = BigDecimal("300.00"),
                    paymentDateTime = dateOfPayment
            ))

            val expectedAmount = paymentRepository
                .totalPaymentsUntilNowByPeriod(wallet.id, dateOfPayment.toLocalDate(),paymentPeriod )

            Assertions.assertEquals(BigDecimal("600.00"),expectedAmount)

        }
    }

    private fun buildWallet(
        ownerName: String = "Owner name",
        limitValue : BigDecimal = BigDecimal("5000.00"),
        payments : MutableList<PaymentModel> = ArrayList()
    ) : WalletModel {
        return WalletModel(ownerName = ownerName, limitValue = limitValue, payments = payments)
    }

    private fun buildPayment (
        id: Long = 1L,
        period: Period = Period.DAYTIME,
        amount: BigDecimal = BigDecimal("1000.00"),
        paymentDateTime: LocalDateTime = LocalDateTime.now(),
        wallet: WalletModel? = null
    ): PaymentModel {
        return PaymentModel(id = id, period =  period, amount = amount, paymentDateTime = paymentDateTime, wallet = wallet)
    }
}