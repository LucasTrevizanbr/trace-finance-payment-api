package com.payment.domain.model

import com.payment.application.enums.Period
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "DIARY_PAYMENT")
data class DiaryPaymentModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Enumerated(EnumType.STRING)
    @Column
    val period: Period,

    @Column
    val amount: BigDecimal,

    @Column
    val paymentDateTime : LocalDateTime,

    @ManyToOne
    val wallet: WalletModel?
) {

}
