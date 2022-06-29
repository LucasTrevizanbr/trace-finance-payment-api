package com.payment.domain.model

import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity(name = "wallet")
data class WalletModel(

    @Id
    @GeneratedValue
    val id: UUID ?,

    @Column
    val ownerName: String,

    @Column
    var limitValue: BigDecimal = BigDecimal.ZERO,

    @OneToMany(mappedBy = "wallet", fetch = FetchType.LAZY)
    val dailyPayments: List<DiaryPaymentModel>?
)
