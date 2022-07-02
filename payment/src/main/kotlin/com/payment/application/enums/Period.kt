package com.payment.application.enums

import com.payment.domain.service.payment.DayTimePaymentStrategy
import com.payment.domain.service.payment.NightlyPaymentStrategy
import com.payment.domain.service.payment.PaymentStrategy
import com.payment.domain.service.payment.WeekendPaymentStrategy

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
