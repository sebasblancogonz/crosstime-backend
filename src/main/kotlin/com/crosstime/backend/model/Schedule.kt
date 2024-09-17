package com.crosstime.backend.model

import com.crosstime.backend.enums.DayOfWeek
import java.time.LocalTime
import java.util.UUID

class Schedule(
    val id: UUID? = null,
    val dayOfWeek: DayOfWeek,
    val timeOfDay: LocalTime
)
