package com.payment.domain.repository

import com.payment.application.enums.Period
import com.payment.domain.model.DiaryPaymentModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.time.LocalDate

@Repository
interface DiaryPaymentRepository : JpaRepository<DiaryPaymentModel, Long> {

   // fun hasLimitInWeekend(currentPeriod: LocalDate) : Boolean

    //@Query("SELECT SUM(dp.amount) FROM DiaryPaymentModel dp WHERE dp.period == :currentPeriod AND dp.paymentDateTime")
    //fun paymentsTotalAmountTodayByPeriod(currentPeriod: Period, toLocalDate: LocalDate?) : BigDecimal
}