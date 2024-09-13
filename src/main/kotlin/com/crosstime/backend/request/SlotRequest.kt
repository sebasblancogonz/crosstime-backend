package com.crosstime.backend.request

import com.crosstime.backend.enums.TrainingType
import java.time.LocalDateTime

data class SlotRequest(
    val capacity: Int,
    val dateTime: LocalDateTime,
    val duration: Int,
    val trainingType: TrainingType
)