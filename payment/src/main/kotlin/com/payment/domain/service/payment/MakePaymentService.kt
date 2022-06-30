package com.payment.domain.service.payment

import com.payment.application.controller.request.MakePaymentRequest
import com.payment.application.enums.Errors
import com.payment.application.enums.Period
import com.payment.application.exception.PeriodLimitReachedException
import com.payment.application.exception.WalletLimitReachedException
import com.payment.application.extension.toPaymentModel
import com.payment.domain.model.PaymentModel
import com.payment.domain.model.WalletModel
import com.payment.domain.service.wallet.CrudPaymentService
import com.payment.domain.service.wallet.CrudWalletService
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import javax.transaction.Transactional

@Service
class MakePaymentService(
    private val zoneId: String = "America/Sao_Paulo",
    private val dateTimeNow: ZonedDateTime = ZonedDateTime.now(ZoneId.of(zoneId)),
    private val isWeekend: Boolean = dateTimeNow.dayOfWeek.value == 1 || dateTimeNow.dayOfWeek.value == 7,
    private val currentPeriod: Period =  if (dateTimeNow.hour in 6..17 )  Period.DAYTIME else Period.NIGHTLY,

    private val currentLimit : LimitStrategy = when{
        isWeekend -> WeekendLimit()
        currentPeriod == Period.DAYTIME -> DaytimeLimit()
        else -> { NightlyLimit()}
    },

    private val crudPaymentService: CrudPaymentService,
    private val crudWalletService: CrudWalletService
) {

    @Transactional
    fun makePayment(walletId: UUID, request: MakePaymentRequest) {
        val wallet = crudWalletService.findById(walletId)
        val payment = request.toPaymentModel()
        val today = dateTimeNow.toLocalDate()

        limitCheck(crudPaymentService.totalPayments(walletId,today),currentLimit.getLimit())
        walletLimitCheck(wallet.limitValue, payment.amount)

        persistPayment(wallet, payment)
        discountWalletLimitAndPersist(wallet, payment)
    }

    private fun limitCheck(totalAmountPayed: BigDecimal, currentLimit: BigDecimal) {
        if (totalAmountPayed >= currentLimit) {
            throw PeriodLimitReachedException(
                Errors.TP203.message.format(currentPeriod, currentLimit),
                Errors.TP203.code
            )
        }
    }

    private fun walletLimitCheck(walletLimitValue: BigDecimal, paymentAmount: BigDecimal) {
        if (walletLimitValue < paymentAmount) {
            throw WalletLimitReachedException(Errors.TP204.message.format(walletLimitValue), Errors.TP204.code)
        }
    }

    private fun persistPayment(wallet: WalletModel, payment: PaymentModel) {
        payment.wallet = wallet
        crudPaymentService.save(payment)
    }

    private fun discountWalletLimitAndPersist(wallet: WalletModel, payment: PaymentModel) {
        wallet.limitValue = wallet.limitValue.minus(payment.amount)
        crudWalletService.save(wallet)
    }
}
