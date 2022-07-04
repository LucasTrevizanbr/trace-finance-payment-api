package com.payment.domain.service.payment

import com.payment.domain.service.payment.strategy.WeekendPaymentStrategy
import com.payment.domain.service.period.PeriodLimitValue
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random


@ExtendWith(MockKExtension::class)
class WeekendStrategyTest {

    private var weekendStrategy: WeekendPaymentStrategy = WeekendPaymentStrategy()

    @Test
    fun `Should get the correct value for weekend limit`(){

        val currentWeekendLimit = weekendStrategy.getCurrentLimit()

        assertEquals(currentWeekendLimit, PeriodLimitValue.WEEKEND_LIMIT.getLimit())
    }

    @Test
    fun `Should return true if the wallet has limit for payment transaction`(){

        val totalPaymentsAlreadyMade = BigDecimal(Random.nextLong(999L, 1000L).toString())
            .setScale(2, RoundingMode.HALF_UP)

        val hasLimit = weekendStrategy.walletStillHavePeriodLimit(totalPaymentsAlreadyMade)

        assertTrue(hasLimit)
    }

    @Test
    fun `Should return false if the wallet has no limit for payment transaction`(){

        val totalPaymentsAlreadyMade = BigDecimal(Random.nextLong(1000L, 1100L).toString())
            .setScale(2, RoundingMode.HALF_UP)

        val hasLimit = weekendStrategy.walletStillHavePeriodLimit(totalPaymentsAlreadyMade)

        assertFalse(hasLimit)
    }

}