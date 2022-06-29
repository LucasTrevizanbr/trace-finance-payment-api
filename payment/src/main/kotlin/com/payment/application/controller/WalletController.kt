package com.payment.application.controller

import com.payment.application.controller.request.PostWalletRequest
import com.payment.application.controller.response.WalletLimitResponseDTO
import com.payment.application.controller.response.WalletResponseDTO
import com.payment.application.extension.toResponse
import com.payment.application.extension.toResponseLimit
import com.payment.domain.service.WalletService
import com.payment.application.extension.toWalletModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import java.util.*

@RestController
@RequestMapping("wallets")
class WalletController (
    val walletService: WalletService
) {

    @PostMapping
    fun create(@RequestBody request:PostWalletRequest) : ResponseEntity<WalletResponseDTO> {
        val wallet = walletService.save(request.toWalletModel())
        return ResponseEntity.status(HttpStatus.CREATED).body(wallet.toResponse());
    }

    @GetMapping("/{walletId}/limits")
    fun getLimits(@PathVariable walletId:UUID) : ResponseEntity<WalletLimitResponseDTO> {
        val wallet = walletService.getLimitsById(walletId)
        return ResponseEntity.ok(wallet.toResponseLimit());
    }
}