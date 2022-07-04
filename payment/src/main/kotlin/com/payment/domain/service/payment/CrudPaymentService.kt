package com.payment.domain.service.payment
import com.payment.domain.service.period.Period
import com.payment.domain.repository.PaymentRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*


@Service
class CrudPaymentService(
    private val paymentRepository: PaymentRepository
) {

    fun getTotalPaymentsByPeriod(walletId: UUID, today: LocalDate, period: Period): BigDecimal {
        return paymentRepository.totalPaymentsUntilNowByPeriod(walletId, today, period) ?: BigDecimal("0")
    }

}