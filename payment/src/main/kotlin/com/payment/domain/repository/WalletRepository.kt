package com.payment.domain.repository

import com.payment.domain.model.WalletModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface WalletRepository : JpaRepository<WalletModel, UUID> {

}