package com.payment.domain.service.payment

import com.payment.application.controller.request.MakePaymentRequest
import com.payment.application.enums.Errors
import com.payment.application.enums.Period
import com.payment.application.exception.PeriodLimitReachedException
import com.payment.application.exception.WalletLimitReachedException
import com.payment.application.extension.toPaymentModel
import com.payment.domain.model.PaymentModel
import com.payment.domain.model.WalletModel
import com.payment.domain.service.wallet.CrudWalletService
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*
import javax.transaction.Transactional

@Service
class MakePaymentService(
    private val crudPaymentService: CrudPaymentService,
    private val crudWalletService: CrudWalletService,
) {

    @Transactional
    fun makePayment(walletId: UUID, request: MakePaymentRequest) {
        val wallet = crudWalletService.findById(walletId)
        val payment = request.toPaymentModel()
        val today = payment.paymentDateTime.toLocalDate()

        val isWeekend: Boolean = isWeekend(payment)
        val currentPeriod = currentPeriod(payment)

        val currentPaymentStrategy : PaymentStrategy = when{
            isWeekend -> WeekendStrategy(crudPaymentService)
            currentPeriod == Period.DAYTIME -> DayTimePaymentStrategy(crudPaymentService)
            else -> { NightlyPaymentStrategy(crudPaymentService)}
        }

        limitCheck(
            currentPaymentStrategy.getTotalPaymentsUntilNow(walletId, today, currentPeriod),
            currentPaymentStrategy.getLimit(),
            currentPeriod
        )

        walletLimitCheck(wallet.limitValue, payment.amount)

        persistPayment(wallet, payment)

        discountWalletLimitAndPersist(wallet, payment)
    }

    private fun isWeekend(payment: PaymentModel): Boolean {
        return payment.paymentDateTime.dayOfWeek.value == 1 || payment.paymentDateTime.dayOfWeek.value == 7
    }

    private fun currentPeriod(payment: PaymentModel): Period {
        return if (payment.paymentDateTime.hour in 6..17) Period.DAYTIME else Period.NIGHTLY
    }

    private fun limitCheck(totalAmountPayedUntilNow: BigDecimal, currentLimit: BigDecimal, currentPeriod : Period) {
        if (totalAmountPayedUntilNow >= currentLimit) {
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
