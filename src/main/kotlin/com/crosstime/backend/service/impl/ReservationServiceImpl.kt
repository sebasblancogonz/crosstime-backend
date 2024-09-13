package com.crosstime.backend.service.impl

import com.crosstime.backend.entity.Reservation
import com.crosstime.backend.exeption.FullSlotException
import com.crosstime.backend.exeption.SlotAlreadyReservedException
import com.crosstime.backend.exeption.SlotNotFoundException
import com.crosstime.backend.exeption.UserNotFoundException
import com.crosstime.backend.repository.ReservationRepository
import com.crosstime.backend.repository.SlotRepository
import com.crosstime.backend.repository.UsersRepository
import com.crosstime.backend.request.ReservationRequest
import com.crosstime.backend.service.ReservationService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ReservationServiceImpl(
    val reservationRepository: ReservationRepository,
    val usersRepository: UsersRepository,
    val slotRepository: SlotRepository
): ReservationService {

    override fun createReservation(reservationRequest: ReservationRequest) {
        val slot = slotRepository.findById(reservationRequest.slotId).orElseThrow {
            throw SlotNotFoundException(reservationRequest.slotId)
        }

        val user = usersRepository.findById(reservationRequest.userId).orElseThrow {
            throw UserNotFoundException()
        }

        if (slot.isFull()) throw FullSlotException()
        if (slot.slotAlreadyReservedByUser(user.id!!)) throw SlotAlreadyReservedException()

        val reservation = Reservation(date = LocalDate.now(), user = user, slot = slot)

        reservationRepository.save(reservation)
    }

}