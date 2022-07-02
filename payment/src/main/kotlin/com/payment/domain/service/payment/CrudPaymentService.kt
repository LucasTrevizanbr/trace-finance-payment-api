package com.payment.domain.service.payment
import com.payment.application.enums.Errors
import com.payment.application.enums.Period
import com.payment.application.exception.NotFoundException
import com.payment.application.exception.PaymentWithoutWalletException
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

    fun getTotalPaymentsByPeriod(walletId: UUID, today: LocalDate, period: Period): BigDecimal {
        return paymentRepository.totalPaymentsUntilNowByPeriod(walletId, today, period) ?: BigDecimal("0")
    }

    @Transactional
    fun save(payment: PaymentModel) : PaymentModel {
        if(payment.wallet == null){
            throw PaymentWithoutWalletException(Errors.TP205.message, Errors.TP205.code)
        }
        return paymentRepository.save(payment)
    }
}