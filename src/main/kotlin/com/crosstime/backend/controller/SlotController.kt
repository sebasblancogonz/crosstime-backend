package com.crosstime.backend.controller

import com.crosstime.backend.model.Slot
import com.crosstime.backend.request.SlotRequest
import com.crosstime.backend.service.SlotService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/slots")
class SlotController(
    val slotService: SlotService
) {

    @GetMapping("/{slotId}")
    fun getSlot(@PathVariable("slotId") slotId: String): ResponseEntity<Any> =
        ResponseEntity.ok(slotService.findSlotById(UUID.fromString(slotId)))

    @PostMapping("/create")
    fun createSlot(@RequestBody request: SlotRequest): ResponseEntity<Any> {
        slotService.createSlot(
            Slot(capacity = request.capacity,
                duration = request.duration,
                trainingType = request.trainingType,
                dateTime = request.dateTime)
        )

        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun getAllSlots(): ResponseEntity<List<Slot>> {
        return ResponseEntity.ok(slotService.findAllSlots())
    }

}