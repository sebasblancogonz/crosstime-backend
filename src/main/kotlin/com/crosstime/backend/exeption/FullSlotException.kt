package com.crosstime.backend.exeption

class FullSlotException : RuntimeException() {
    override val message: String
        get() = "Could not create reservation. Slot is full."

}