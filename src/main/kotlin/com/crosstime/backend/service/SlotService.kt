package com.crosstime.backend.service

import com.crosstime.backend.model.Slot
import java.util.UUID

interface SlotService {

    fun findSlotsByDate(date: String): List<Slot>

    fun findAllSlots(): List<Slot>

    fun findSlotById(id: UUID): Slot

    fun createSlot(slot: Slot)

}