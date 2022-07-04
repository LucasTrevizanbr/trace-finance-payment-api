package com.payment.domain.repository

import com.payment.domain.service.period.Period
import com.payment.domain.model.PaymentModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Repository
interface PaymentRepository : JpaRepository<PaymentModel, Long> {

    @Query("SELECT SUM(dp.amount) " +
            "FROM PaymentModel dp " +
            "WHERE dp.wallet.id = :walletId " +
            "AND dp.period LIKE :period "+
            "AND cast(dp.paymentDateTime as LocalDate) = :todayDate")
    fun totalPaymentsUntilNowByPeriod(walletId: UUID, todayDate: LocalDate?, period: Period) : BigDecimal?

}