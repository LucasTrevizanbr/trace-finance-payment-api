package com.payment.application.controller

import com.payment.application.controller.request.MakePaymentRequest
import com.payment.application.controller.request.PostWalletRequest
import com.payment.application.controller.response.WalletLimitResponseDTO
import com.payment.application.controller.response.CreateWalletResponseDTO
import com.payment.application.extension.toPaymentModel
import com.payment.application.extension.toResponse
import com.payment.application.extension.toResponseLimitValue
import com.payment.domain.service.wallet.CrudWalletService
import com.payment.application.extension.toWalletModel
import com.payment.domain.service.payment.MakePaymentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("wallets")
class WalletController (
    val walletService: CrudWalletService,
    val paymentService: MakePaymentService
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
        paymentService.makePayment(wallet, request.toPaymentModel())
    }
}