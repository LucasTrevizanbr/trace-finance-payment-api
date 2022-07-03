package com.payment.domain.model

import java.math.BigDecimal
import java.util.*
import javax.persistence.*
import kotlin.collections.ArrayList

@Entity
@Table(name = "wallet")
data class WalletModel(

    @Id
    @GeneratedValue
    val id: UUID = UUID.randomUUID(),

    @Column
    val ownerName: String,

    @Column
    var limitValue: BigDecimal = BigDecimal("5000.00"),

    @OneToMany(mappedBy = "wallet", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var payments: MutableList<PaymentModel>? = ArrayList()

){

    fun linkPaymentAndWallet(payment: PaymentModel){
        this.payments?.add(payment)
        payment.wallet = this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WalletModel

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
