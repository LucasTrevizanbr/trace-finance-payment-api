package com.payment.domain.model

import com.payment.application.enums.Period
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "payment")
data class PaymentModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ?,

    @Enumerated(EnumType.STRING)
    @Column
    val period: Period,

    @Column
    val amount: BigDecimal,

    @Column
    val paymentDateTime : LocalDateTime,

    @ManyToOne
    var wallet: WalletModel ?
)
