package com.crosstime.backend.exeption

import java.util.UUID

class SlotNotFoundException(
    private val slotId: UUID? = null
) : RuntimeException() {
    override val message: String
        get() = slotId?.let {
            "Slot with id $slotId not found."
        }?: "Slot not found."

}