package com.payment.application.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.payment.application.controller.request.MakePaymentRequest
import com.payment.application.controller.request.PostWalletRequest
import com.payment.domain.service.period.Period
import com.payment.domain.model.PaymentModel
import com.payment.domain.model.WalletModel
import com.payment.domain.repository.PaymentRepository
import com.payment.domain.repository.WalletRepository
import com.payment.domain.service.period.PeriodDefinitionService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.random.Random

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles("test")
class WalletControllerTest{

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var walletRepository: WalletRepository

    @Autowired
    private lateinit var paymentRepository: PaymentRepository

    @Autowired
    private lateinit var periodDefinitionService: PeriodDefinitionService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun cleanDatabase() = walletRepository.deleteAll()

    @AfterEach
    fun tearDown() = walletRepository.deleteAll()

    @Nested
    inner class `Create wallet test` {

        @Test
        fun `should create a wallet`() {

            val request = PostWalletRequest("${Random.nextInt()}")

            mockMvc.perform(
                post("/wallets")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
                .andExpect(status().isCreated)
                .andExpect(jsonPath("id").isNotEmpty)
                .andExpect(jsonPath("ownerName").isNotEmpty)

            val walletSaved = walletRepository.findAll().toList()
            assertEquals(1, walletSaved.size)
            assertEquals(request.ownerName,walletSaved[0].ownerName )
        }

        @Test
        fun `should return a message error when trying to create a wallet with empty ownerName`() {
            val request = PostWalletRequest("  ")

            mockMvc.perform(
                post("/wallets")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
                .andExpect(status().isUnprocessableEntity)
                .andExpect(jsonPath("httpCode").value(422))
                .andExpect(jsonPath("message").value("Invalid request"))
                .andExpect(jsonPath("internalCode").value("TP-001"))
                .andExpect(jsonPath("errors[0].message")
                    .value("Wallet must have a valid owner name, can't be empty"))
                .andExpect(jsonPath("errors[0].field").value("ownerName"))
        }

    }

    @Nested
    inner class `Get wallet's limit test` {

        @Test
        fun `should get a limit from a wallet`() {

            val savedWallet = walletRepository.save(buildWallet());

            mockMvc.perform(get("/wallets/${savedWallet.id}/limits"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("value").isNumber )
                .andExpect(jsonPath("value").value("5000.0") )

            val walletSaved = walletRepository.findAll().toList()
            assertEquals(1, walletSaved.size)
            assertEquals(BigDecimal("5000.00"),walletSaved[0].limitValue )
        }

        @Test
        fun `should return a message error when trying to get a limit of a nonExisting wallet `() {
            val nonExistentUUID = UUID.randomUUID()

            mockMvc.perform(get("/wallets/${nonExistentUUID}/limits"))
                .andExpect(status().isNotFound)
                .andExpect(jsonPath("httpCode").value(404))
                .andExpect(jsonPath("message").value("Wallet with id: [$nonExistentUUID] doesn't found"))
                .andExpect(jsonPath("internalCode").value("TP-101"))
        }
    }

    @Nested
    inner class `Make payment test` {

        @Test
        fun `should successfully make a payment`() {

            val savedWallet = walletRepository.save(buildWallet());

            val request = MakePaymentRequest(BigDecimal("999.99"), LocalDate.of(2022,1,8))

            mockMvc.perform(
                post("/wallets/${savedWallet.id}/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            )
                .andExpect(status().isOk)

            val walletSaved = walletRepository.findAll().toList()
            assertEquals(BigDecimal("4000.01"), walletSaved[0].limitValue)
        }

        @Test
        fun `should send a error message when trying to make a payment to a nonexistent wallet `() {
            val request = MakePaymentRequest(BigDecimal("999.99"), LocalDate.of(2022,1,8))
            val nonexistentUUID = UUID.randomUUID()
            
            mockMvc.perform(
                post("/wallets/${nonexistentUUID}/payments")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
                .andExpect(status().isNotFound)
                .andExpect(jsonPath("httpCode").value(404))
                .andExpect(jsonPath("message")
                    .value("Wallet with id: [${nonexistentUUID}] doesn't found"))
                .andExpect(jsonPath("internalCode").value("TP-101"))
        }

        @Test
        fun `should send a error message when trying to make a payment over the limit transaction `() {
            val request = MakePaymentRequest(BigDecimal("1001.00"), LocalDate.of(2022,1,8))

            mockMvc.perform(
                post("/wallets/${UUID.randomUUID()}/payments")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
                .andExpect(status().isUnprocessableEntity)
                .andExpect(jsonPath("httpCode").value(422))
                .andExpect(jsonPath("message").value("Invalid request"))
                .andExpect(jsonPath("internalCode").value("TP-001"))
                .andExpect(jsonPath("errors[0].message")
                    .value("Payment limit is R$1000.00 for transaction"))
                .andExpect(jsonPath("errors[0].field").value("amount"))
        }

        @Test
        fun `should send a error message when trying to make a payment when wallet has no more limit `() {
            val wallet = walletRepository.save(buildWallet(limitValue = BigDecimal("999.99")))
            val request = MakePaymentRequest(BigDecimal("1000.00"), LocalDate.of(2022,1,8))

            mockMvc.perform(
                post("/wallets/${wallet.id}/payments")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("httpCode").value(400))
                .andExpect(jsonPath("message").value("The wallet has no limit for this payment"))
                .andExpect(jsonPath("internalCode").value("TP-204"))
        }

        @Test
        fun `should send a error message when trying to make a payment when wallet has no more limit by period `() {

            val dateTimePayments = periodDefinitionService.getBrazilianDateTimeNow()
            val request = MakePaymentRequest(BigDecimal("1000.00"), dateTimePayments.toLocalDate())

            var wallet = walletRepository.save(buildWallet())
            paymentRepository.save(buildPayment(paymentDateTime = dateTimePayments, period = Period.WEEKEND, wallet = wallet))


            mockMvc.perform(
                post("/wallets/${wallet.id}/payments")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("httpCode").value(400))
                .andExpect(jsonPath("message").value("The WEEKEND limit of R$1000.00 is already been reached"))
                .andExpect(jsonPath("internalCode").value("TP-203"))
        }
    }

    private fun buildWallet(
        ownerName: String = "Owner name",
        limitValue: BigDecimal = BigDecimal("5000.00"),
    ) : WalletModel {
        return WalletModel(ownerName = ownerName, limitValue = limitValue)
    }

    private fun buildPayment (
        id : Long? = null,
        period: Period = Period.DAYTIME,
        amount: BigDecimal = BigDecimal("1000.00"),
        paymentDateTime: LocalDateTime = LocalDateTime.now(),
        wallet: WalletModel? = null
    ): PaymentModel {
        return PaymentModel(id, period, amount, paymentDateTime, wallet)
    }


}