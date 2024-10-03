package com.crosstime.backend.service

import com.crosstime.backend.model.Reservation
import com.crosstime.backend.request.ReservationRequest
import java.util.UUID

interface ReservationService {

    fun createReservation(reservationRequest: ReservationRequest)

    fun getReservationsByUserId(userId: UUID): List<Reservation>

    fun getReservationsBySlotId(slotId: UUID): List<Reservation>

    fun deleteReservation(reservationId: UUID)

}