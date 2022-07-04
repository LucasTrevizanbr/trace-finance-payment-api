package com.payment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class PaymentApplication

fun main(args: Array<String>) {
	runApplication<PaymentApplication>(*args)
}

