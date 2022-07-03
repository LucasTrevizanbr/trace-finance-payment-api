package com.payment

import com.payment.application.controller.WalletController
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles


@SpringBootTest
@ActiveProfiles("dev")
class PaymentApplicationTests(
    @Autowired val walletController: WalletController
){

    @Test
    fun `Should load context `(){
        walletController
        Assertions.assertNotNull(walletController)
    }
}
