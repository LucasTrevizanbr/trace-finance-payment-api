package com.payment.domain.service.period


import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.*

@ExtendWith(MockKExtension::class)
class PeriodDefinitionServiceTest{

    private lateinit var periodDefinitionService: PeriodDefinitionService

    @Test
    fun `should get the DAYTIME period`(){
        var zoneId = "America/Sao_Paulo"
        var weekDate = LocalDate.of(2022,7,1)
        var dateTimeNow = LocalDateTime.of(weekDate, LocalTime.of(17, 59, 59))

        periodDefinitionService = PeriodDefinitionService(zoneId,dateTimeNow )

        val currentPeriod = periodDefinitionService.getPeriod()

        assertEquals(Period.DAYTIME, currentPeriod)
    }

    @Test
    fun `should get the NIGHTLY period`(){
        var zoneId = "America/Sao_Paulo"
        var weekDate = LocalDate.of(2022,7,1)
        var dateTimeNow = LocalDateTime.of(weekDate, LocalTime.of(18, 0,0))

        periodDefinitionService = PeriodDefinitionService(zoneId,dateTimeNow )

        val currentPeriod = periodDefinitionService.getPeriod()

        assertEquals(Period.NIGHTLY, currentPeriod)
    }

    @Test
    fun `should get the WEEKEND period`(){
        var zoneId = "America/Sao_Paulo"
        var weekendDate = LocalDate.of(2022,7,2)
        var dateTimeNow = LocalDateTime.of(weekendDate, LocalTime.of(18, 0,0))

        periodDefinitionService = PeriodDefinitionService(zoneId,dateTimeNow )

        val currentPeriod = periodDefinitionService.getPeriod()

        assertEquals(Period.NIGHTLY, currentPeriod)
    }
}