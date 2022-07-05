package com.payment.domain.service.period

import org.springframework.stereotype.Service
import java.time.*

@Service
class PeriodDefinitionService(
    private val zoneId: String = "America/Sao_Paulo",
    private val dateTimeNow: LocalDateTime = LocalDateTime.now(ZoneId.of(zoneId)),
) {

    fun getPeriod(): Period {

        return if
                (dateTimeNow.dayOfWeek == DayOfWeek.SUNDAY || dateTimeNow.dayOfWeek == DayOfWeek.FRIDAY)
                Period.WEEKEND
        else if
                (dateTimeNow.hour in 6..17) return Period.DAYTIME
        else
            return Period.NIGHTLY
    }

    fun getBrazilianDateTimeNow() : LocalDateTime {
        return LocalDateTime.now(ZoneId.of(zoneId))
    }
}

