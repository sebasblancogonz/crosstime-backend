package com.crosstime.backend.exeption

class SlotAlreadyReservedException : RuntimeException() {
    override val message: String
        get() = "Slot cannot be reserved twice by the same user."

}