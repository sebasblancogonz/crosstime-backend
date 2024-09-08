package com.crosstime.backend.model

import java.time.DayOfWeek
import java.time.LocalTime
import java.util.UUID

class Schedule(
    val id: UUID? = null,
    val dayOfWeek: DayOfWeek,
    val timeOfDay: LocalTime,
    val slot: Slot
)
