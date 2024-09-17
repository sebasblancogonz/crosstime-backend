package com.crosstime.backend.request

import com.crosstime.backend.enums.DayOfWeek
import java.time.LocalTime

data class ScheduleRequest(
    val dayOfWeek: DayOfWeek,
    val timeOfDay: LocalTime
)
