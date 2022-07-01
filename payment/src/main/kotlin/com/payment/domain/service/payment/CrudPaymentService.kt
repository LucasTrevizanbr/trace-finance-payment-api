package com.payment.domain.service.payment
import com.payment.application.enums.Period
import com.payment.domain.model.PaymentModel
import com.payment.domain.repository.PaymentRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import javax.transaction.Transactional

@Service
class CrudPaymentService(
    private val paymentRepository: PaymentRepository
) {

    fun totalPayments(walletId: UUID, today: LocalDate): BigDecimal{
        return paymentRepository.paymentsAmountUntilNow(walletId,today) ?: BigDecimal("0")
    }

    fun totalPaymentsByPeriod(walletId: UUID, today: LocalDate, period: Period): BigDecimal {
        return paymentRepository.paymentsAmountUntilNowByPeriod(walletId, today, period) ?: BigDecimal("0")
    }

    @Transactional
    fun save(payment: PaymentModel) : PaymentModel {
        return paymentRepository.save(payment)
    }
}