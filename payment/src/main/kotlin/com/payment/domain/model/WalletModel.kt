package com.payment.domain.model

import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "wallet")
data class WalletModel(

    @Id
    @GeneratedValue
    val id: UUID ?,

    @Column
    val ownerName: String,

    @Column
    var limitValue: BigDecimal = BigDecimal("5000.00"),

    @OneToMany(mappedBy = "wallet", fetch = FetchType.LAZY)
    val payments: List<PaymentModel>?
)
