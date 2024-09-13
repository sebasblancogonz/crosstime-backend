package com.crosstime.backend.service.impl

import com.crosstime.backend.exeption.SlotNotFoundException
import com.crosstime.backend.mapper.SlotMapper
import com.crosstime.backend.model.Slot
import com.crosstime.backend.repository.SlotRepository
import com.crosstime.backend.service.SlotService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.UUID

@Service
class SlotServiceImpl(
    private val slotRepository: SlotRepository,
    private val slotMapper: SlotMapper
) : SlotService {

    override fun findSlotsByDate(date: String): List<Slot> {
        val localDate = LocalDate.parse(date)
        return slotMapper.mapEntitiesToModels(slotRepository.findAllByLocalDate(localDate))
    }

    override fun findAllSlots(): List<Slot> {
        return slotMapper.mapEntitiesToModels(slotRepository.findAll())
    }

    override fun findSlotById(id: UUID): Slot {
        return slotRepository.findById(id).map { slotMapper.mapEntityToModel(it) }.orElseThrow {
            throw SlotNotFoundException(id)
        }
    }
}