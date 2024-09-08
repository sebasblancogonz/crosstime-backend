package com.crosstime.backend.model

import com.crosstime.backend.enums.ScheduleExceptionType
import java.time.LocalDate
import java.util.UUID

class ScheduleException(
    val id: UUID? = null,
    val specificDate: LocalDate,
    val slot: Slot? = null,
    val exceptionType: ScheduleExceptionType
)