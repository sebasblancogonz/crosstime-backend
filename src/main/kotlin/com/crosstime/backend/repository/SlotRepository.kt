package com.crosstime.backend.repository

import com.crosstime.backend.entity.Slot
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface SlotRepository : JpaRepository<Slot, UUID>