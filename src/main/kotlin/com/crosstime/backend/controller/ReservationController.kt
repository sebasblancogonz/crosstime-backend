package com.crosstime.backend.controller

import com.crosstime.backend.request.ReservationRequest
import com.crosstime.backend.service.ReservationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/reservations")
class ReservationController (
    val reservationService: ReservationService
) {

    @PostMapping("/create")
    fun createReservation(@RequestBody request: ReservationRequest): ResponseEntity<Void> {
        reservationService.createReservation(request)
        return ResponseEntity.ok().build()
    }

}