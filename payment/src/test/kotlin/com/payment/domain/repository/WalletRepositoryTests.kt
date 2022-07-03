package com.payment.domain.repository

import com.payment.domain.model.PaymentModel
import com.payment.domain.model.WalletModel
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal

@ExtendWith(MockKExtension::class)
@SpringBootTest
@ActiveProfiles("dev")
class WalletRepositoryTests {

    @Autowired
    private lateinit var walletRepository: WalletRepository

    @BeforeEach
    fun cleanDataBase () = walletRepository.deleteAll()

    private val firstLimitOfTheDay = BigDecimal("5000.00")

    @Test
    fun `should reset wallet limit`(){
        val savedWallet = walletRepository.save(buildWallet( limitValue = BigDecimal("3000.00")))

        walletRepository.resetWalletsLimits(firstLimitOfTheDay)

        val expectWallet = walletRepository.findById(savedWallet.id).get()
        Assertions.assertEquals(expectWallet.limitValue, firstLimitOfTheDay )
    }

    private fun buildWallet(
        ownerName: String = "Owner name",
        limitValue: BigDecimal = BigDecimal("5000.00"),
        payments: MutableList<PaymentModel> = ArrayList()
    ) : WalletModel {
        return WalletModel(ownerName = ownerName, limitValue = limitValue, payments = payments)
    }
}