package com.crosstime.backend.request

import com.crosstime.backend.model.Slot
import java.time.DayOfWeek
import java.time.LocalTime

data class ScheduleRequest(
    val dayOfWeek: DayOfWeek,
    val timeOfDay: LocalTime
)
