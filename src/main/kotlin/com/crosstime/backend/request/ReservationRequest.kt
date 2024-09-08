package com.crosstime.backend.request

import java.util.UUID

data class ReservationRequest(
    val userId: UUID,
    val slotId: UUID
)