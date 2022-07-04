package com.payment.domain.service.period

import com.payment.domain.service.payment.strategy.DayTimePaymentStrategy
import com.payment.domain.service.payment.strategy.NightlyPaymentStrategy
import com.payment.domain.service.payment.strategy.PaymentStrategy
import com.payment.domain.service.payment.strategy.WeekendPaymentStrategy

enum class Period (

) {
    DAYTIME {
        override fun getPeriodStrategy(): PaymentStrategy {
            return DayTimePaymentStrategy()
        }
    },
    NIGHTLY {
        override fun getPeriodStrategy(): PaymentStrategy {
            return NightlyPaymentStrategy()
        }
    },
    WEEKEND {
        override fun getPeriodStrategy(): PaymentStrategy {
            return WeekendPaymentStrategy()
        }
    };

    abstract fun getPeriodStrategy() : PaymentStrategy
}
