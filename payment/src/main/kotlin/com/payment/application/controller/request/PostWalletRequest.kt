package com.payment.application.controller.request

import com.payment.domain.model.WalletModel

data class PostWalletRequest(
    val ownerName: String
) {
    fun toWalletModel(): WalletModel {
        return WalletModel(id = null, ownerName = this.ownerName)
    }
}