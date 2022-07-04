package com.payment.application.controller

import com.payment.application.controller.request.MakePaymentRequest
import com.payment.application.controller.request.PostWalletRequest
import com.payment.application.controller.response.CreateWalletResponseDTO
import com.payment.application.controller.response.WalletLimitResponseDTO
import com.payment.infra.extension.toPaymentModel
import com.payment.infra.extension.toResponse
import com.payment.infra.extension.toResponseLimitValue
import com.payment.infra.extension.toWalletModel
import com.payment.domain.service.payment.MakePaymentService
import com.payment.domain.service.period.PeriodDefinitionService
import com.payment.domain.service.wallet.CrudWalletService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@CrossOrigin(allowedHeaders = ["*"], origins = ["*"])
@RestController
@RequestMapping("wallets")
class WalletController (
    private val walletService: CrudWalletService,
    private val paymentService: MakePaymentService,
    private val periodDefinitionService: PeriodDefinitionService
) {

    @GetMapping("/{walletId}/limits")
    fun getLimits(@PathVariable walletId:UUID) : ResponseEntity<WalletLimitResponseDTO> {
        val wallet = walletService.findById(walletId)
        return ResponseEntity.ok(wallet.toResponseLimitValue());
    }

    @PostMapping
    fun create(@RequestBody @Valid request:PostWalletRequest) : ResponseEntity<CreateWalletResponseDTO> {
        val wallet = walletService.save(request.toWalletModel())
        return ResponseEntity.status(HttpStatus.CREATED).body(wallet.toResponse());
    }

    @PostMapping("/{walletId}/payments")
    @ResponseStatus(HttpStatus.OK)
    fun makePayment(@RequestBody @Valid request:MakePaymentRequest, @PathVariable walletId: UUID){
        val wallet = walletService.findById(walletId);
        val brazilTime = periodDefinitionService.getBrazilianDateTimeNow().toLocalTime()
        val payment = request.toPaymentModel(periodDefinitionService.getPeriod(), brazilTime)
        paymentService.makePayment(wallet, payment)
    }
}