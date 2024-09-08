package com.crosstime.backend.service

import com.crosstime.backend.model.Slot

interface SlotService {

    fun findSlotsByDate(date: String): List<Slot>

    fun findAllSlots(): List<Slot>

}