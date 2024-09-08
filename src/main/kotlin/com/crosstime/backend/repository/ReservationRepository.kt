package com.crosstime.backend.repository

import com.crosstime.backend.entity.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ReservationRepository : JpaRepository<Reservation, UUID>