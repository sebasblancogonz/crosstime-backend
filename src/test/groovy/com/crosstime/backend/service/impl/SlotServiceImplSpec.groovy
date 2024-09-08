package com.crosstime.backend.service.impl

import com.crosstime.backend.enums.TrainingType
import com.crosstime.backend.mapper.SlotMapper
import com.crosstime.backend.repository.SlotRepository
import com.crosstime.backend.entity.Slot as SlotEntity
import com.crosstime.backend.model.Slot as SlotModel
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class SlotServiceImplSpec extends Specification {

    private SlotRepository slotRepository = Mock(SlotRepository.class)
    private SlotMapper slotMapper = Mock(SlotMapper.class)

    private SlotServiceImpl slotService

    def setup() {
        slotService = new SlotServiceImpl(slotRepository, slotMapper)
    }

    def "should return all the slots for a date"() {
        given: "an expected list of slots to be returned"
        def id = UUID.randomUUID()
        def expectedSlotEntities = [buildSlotEntity(id, LocalDateTime.parse("2019-01-01T09:00:00"))]

        when: "the method is called to return the slots"
        def result = slotService.findSlotsByDate("2019-01-01")

        then: "the repository is invoked"
        1 * slotRepository.findAllByLocalDate(LocalDate.parse("2019-01-01")) >> [expectedSlotEntities]

        and: "the mapper is invoked"
        1 * slotMapper.mapEntitiesToModels([expectedSlotEntities]) >> [buildSlotModel(id, LocalDateTime.parse("2019-01-01T09:00:00"))]

        then: "the returned slots are the correct ones"
        assert result.size() == 1
        assert result[0].id == expectedSlotEntities[0].id
    }

    def "should return all the slots"() {
        given: "an expected list of slots to be returned"
        def id = UUID.randomUUID()
        def expectedSlotEntities = [buildSlotEntity(id, LocalDateTime.parse("2019-01-01T09:00:00"))]

        when: "the method is called to return the slots"
        def result = slotService.findAllSlots()

        then: "the repository is invoked"
        1 * slotRepository.findAll() >> [expectedSlotEntities]

        and: "the mapper is invoked"
        1 * slotMapper.mapEntitiesToModels([expectedSlotEntities]) >> [buildSlotModel(id, LocalDateTime.parse("2019-01-01T09:00:00"))]

        then: "the returned slots are the correct ones"
        assert result.size() == 1
        assert result[0].id == expectedSlotEntities[0].id
    }

    def buildSlotEntity(UUID id, LocalDateTime dateTime) {
        return new SlotEntity(id, 20, dateTime, 40, TrainingType.WOD, null, null)
    }

    def buildSlotModel(UUID id, LocalDateTime dateTime) {
        return new SlotModel(id, 20, dateTime, 40, TrainingType.WOD, null)
    }
}
