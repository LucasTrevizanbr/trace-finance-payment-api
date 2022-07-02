package com.payment.domain.service.payment

import com.payment.application.enums.PeriodLimitValue
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
class DayTimePaymentStrategyTest{

    private var dayTimePaymentStrategy = DayTimePaymentStrategy()

    @Test
    fun `Should get the correct value for weekend limit`(){

        val currentWeekendLimit = dayTimePaymentStrategy.getCurrentLimit()

        assertEquals(currentWeekendLimit, PeriodLimitValue.DAYTIME_LIMIT.getLimit())
    }

    @Test
    fun `Should return true if the wallet has limit for payment transaction`(){

        val totalPaymentsAlreadyMade = BigDecimal(Random.nextLong(3999L, 4000L).toString())
            .setScale(2, RoundingMode.HALF_UP)

        val hasLimit = dayTimePaymentStrategy.walletStillHavePeriodLimit(totalPaymentsAlreadyMade)

        assertTrue(hasLimit)
    }

    @Test
    fun `Should return false if the wallet has no limit for payment transaction`(){

        val totalPaymentsAlreadyMade = BigDecimal(Random.nextLong(4000L, 4100L).toString())
            .setScale(2, RoundingMode.HALF_UP)

        val hasLimit = dayTimePaymentStrategy.walletStillHavePeriodLimit(totalPaymentsAlreadyMade)

        assertFalse(hasLimit)
    }
}