package com.crosstime.backend.service

import com.crosstime.backend.request.ReservationRequest
import java.util.UUID

interface ReservationService {

    fun reserveSlot(reservationRequest: ReservationRequest)

}