package com.payment.application.controller

import com.payment.application.controller.request.PostWalletRequest
import com.payment.domain.model.WalletModel
import com.payment.domain.repository.WalletRepository
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.util.*

@RestController
@RequestMapping("wallets")
class WalletController (
    val walletRepository: WalletRepository
) {

    @PostMapping
    fun create(@RequestBody walletRequest:PostWalletRequest) : WalletModel {
        return walletRepository.save(walletRequest.toWalletModel());
    }

    @GetMapping("/{walletId}/limits")
    fun getLimits(@PathVariable walletId:UUID) : BigDecimal{
        return walletRepository.findById(walletId).get().limit_value
    }
}