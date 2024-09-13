package com.crosstime.backend.repository

import com.crosstime.backend.entity.Slot
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.UUID

@Repository
interface SlotRepository : JpaRepository<Slot, UUID> {

    @Query(
        value = """
            select s from Slot s where cast(s.dateTime as localdate) = :date
            """
    )
    fun findAllByLocalDate(date: LocalDate): List<Slot>
}