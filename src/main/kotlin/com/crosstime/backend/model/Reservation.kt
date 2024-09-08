package com.crosstime.backend.model

import java.util.UUID

class Reservation(
    val id: UUID? = null,
    val user: User,
    val slot: Slot
)