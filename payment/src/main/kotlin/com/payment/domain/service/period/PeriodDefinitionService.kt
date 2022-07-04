package com.payment.domain.service.period

import org.springframework.stereotype.Service
import java.time.*

@Service
class PeriodDefinitionService(
    private val zoneId: String = "America/Sao_Paulo",
    private val dateTimeNow: LocalDateTime? = LocalDateTime.now(),
    private val currentDateTime : ZonedDateTime = ZonedDateTime.of(dateTimeNow, ZoneId.of(zoneId))
) {

    fun getPeriod(): Period {

        return if
                (currentDateTime.dayOfWeek == DayOfWeek.SUNDAY || currentDateTime.dayOfWeek == DayOfWeek.MONDAY)  Period.WEEKEND
        else if
                (currentDateTime.hour in 6..17) Period.DAYTIME
        else
            Period.NIGHTLY
    }

    fun getBrazilianDateTimeNow() : LocalDateTime{
        return currentDateTime.toLocalDateTime()
    }
}