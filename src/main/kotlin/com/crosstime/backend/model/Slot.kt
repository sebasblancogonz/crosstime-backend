package com.crosstime.backend.model

import com.crosstime.backend.enums.TrainingType
import java.time.LocalDateTime
import java.util.UUID

class Slot(
    val id: UUID? = null,
    val capacity: Int,
    val dateTime: LocalDateTime,
    val duration: Int,
    val trainingType: TrainingType,
    val instructor: User? = null
)