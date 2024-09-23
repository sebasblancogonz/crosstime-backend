package com.crosstime.backend.repository

import com.crosstime.backend.entity.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ReservationRepository : JpaRepository<Reservation, UUID> {

    fun findByUserId(userId: UUID): List<Reservation>

    fun findBySlotId(slotId: UUID): List<Reservation>

}