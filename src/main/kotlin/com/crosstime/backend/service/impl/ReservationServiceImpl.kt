package com.crosstime.backend.service.impl

import com.crosstime.backend.entity.Reservation
import com.crosstime.backend.exeption.FullSlotException
import com.crosstime.backend.exeption.SlotNotFoundException
import com.crosstime.backend.exeption.UserNotFoundException
import com.crosstime.backend.repository.ReservationRepository
import com.crosstime.backend.repository.SlotRepository
import com.crosstime.backend.repository.UsersRepository
import com.crosstime.backend.request.ReservationRequest
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.UUID

@Service
class ReservationServiceImpl(
    val reservationRepository: ReservationRepository,
    val usersRepository: UsersRepository,
    val slotRepository: SlotRepository
) {

    fun createReservation(request: ReservationRequest) {
        val slot = slotRepository.findById(request.slotId).orElseThrow {
            throw SlotNotFoundException(request.slotId)
        }

        val user = usersRepository.findById(request.userId).orElseThrow {
            throw UserNotFoundException()
        }

        if (slot.isFull()) throw FullSlotException()

        val reservation = Reservation(date = LocalDate.now(), user = user, slot = slot)

        reservationRepository.save(reservation)
    }

}