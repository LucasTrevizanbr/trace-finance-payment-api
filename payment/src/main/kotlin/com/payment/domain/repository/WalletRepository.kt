package com.payment.domain.repository

import com.payment.domain.model.WalletModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.*
import javax.transaction.Transactional

@Repository
interface WalletRepository : JpaRepository<WalletModel, UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE WalletModel W SET W.limitValue = :limit")
    fun resetWalletsLimits(limit : BigDecimal)

}