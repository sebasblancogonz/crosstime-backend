package com.crosstime.backend.controller

import com.crosstime.backend.model.Reservation
import com.crosstime.backend.request.ReservationRequest
import com.crosstime.backend.service.ReservationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID


@RestController
@RequestMapping("/api/reservations")
class ReservationController (
    val reservationService: ReservationService
) {

    @PostMapping("/create")
    fun createReservation(@RequestBody request: ReservationRequest): ResponseEntity<Unit> {
        reservationService.createReservation(request)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/user-id/{userId}")
    fun getReservationsByUserId(@PathVariable userId: String): ResponseEntity<List<Reservation>> =
        ResponseEntity.ok(reservationService.getReservationsByUserId(UUID.fromString(userId)))

    @GetMapping("/slot-id/{slotId}")
    fun getReservationsBySlotId(@PathVariable slotId: String): ResponseEntity<List<Reservation>> =
        ResponseEntity.ok(reservationService.getReservationsBySlotId(UUID.fromString(slotId)))

}