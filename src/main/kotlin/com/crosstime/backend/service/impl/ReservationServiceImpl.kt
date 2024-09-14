package com.crosstime.backend.service.impl

import com.crosstime.backend.exeption.FullSlotException
import com.crosstime.backend.exeption.SlotAlreadyReservedException
import com.crosstime.backend.exeption.SlotNotFoundException
import com.crosstime.backend.exeption.UserNotFoundException
import com.crosstime.backend.mapper.ReservationMapper
import com.crosstime.backend.repository.ReservationRepository
import com.crosstime.backend.repository.SlotRepository
import com.crosstime.backend.repository.UsersRepository
import com.crosstime.backend.request.ReservationRequest
import com.crosstime.backend.service.ReservationService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.UUID
import com.crosstime.backend.entity.Reservation as ReservationEntity
import com.crosstime.backend.model.Reservation as ReservationModel

@Service
class ReservationServiceImpl(
    val reservationRepository: ReservationRepository,
    val reservationMapper: ReservationMapper,
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

        val reservation = ReservationEntity(date = LocalDate.now(), user = user, slot = slot)

        reservationRepository.save(reservation)
    }

    override fun getReservationsByUserId(userId: UUID): List<ReservationModel> =
        reservationMapper.mapEntitiesToModels(reservationRepository.findByUserId(userId))

}