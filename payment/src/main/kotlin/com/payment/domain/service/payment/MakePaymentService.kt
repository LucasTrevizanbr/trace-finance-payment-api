package com.payment.domain.service.payment

import com.payment.application.enums.Errors
import com.payment.application.exception.PeriodLimitReachedException
import com.payment.application.exception.WalletLimitReachedException
import com.payment.domain.model.PaymentModel
import com.payment.domain.model.WalletModel
import com.payment.domain.service.wallet.CrudWalletService
import org.springframework.stereotype.Service
import java.math.BigDecimal
import javax.transaction.Transactional

@Service
class MakePaymentService(
    private val crudPaymentService: CrudPaymentService,
    private val crudWalletService: CrudWalletService,

) {

    @Transactional
    fun makePayment(wallet: WalletModel, payment: PaymentModel) {

        val periodPaymentStrategy : PaymentStrategy = payment.period.getPeriodStrategy()
        val today = payment.paymentDateTime.toLocalDate()
        val amountUntilNow = crudPaymentService.getTotalPaymentsByPeriod(wallet.id, today, payment.period)

        if(!periodPaymentStrategy.walletStillHavePeriodLimit(amountUntilNow)) throw PeriodLimitReachedException(
            Errors.TP203.message.format(payment.period, periodPaymentStrategy.getCurrentLimit()),
            Errors.TP203.code
        )

        if(!walletHasLimit(wallet.limitValue, payment.amount)) throw WalletLimitReachedException(
            Errors.TP204.message.format(wallet.limitValue),
            Errors.TP204.code
        )

        wallet.limitValue = newLimitAfterDiscount(wallet.limitValue, payment.amount)
        wallet.linkPaymentAndWallet(payment)

        crudWalletService.save(wallet)

    }

    private fun walletHasLimit(walletLimitValue: BigDecimal, paymentAmount: BigDecimal) : Boolean {
        return walletLimitValue >= paymentAmount
    }

    private fun newLimitAfterDiscount(walletCurrentLimit : BigDecimal , paymentAmount: BigDecimal) : BigDecimal {
        return walletCurrentLimit.minus(paymentAmount)
    }
}
