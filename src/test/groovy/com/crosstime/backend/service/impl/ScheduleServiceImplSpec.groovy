package com.crosstime.backend.service.impl

import com.crosstime.backend.enums.DayOfWeek
import com.crosstime.backend.mapper.ScheduleMapper
import com.crosstime.backend.model.Schedule as ScheduleModel
import com.crosstime.backend.entity.Schedule as ScheduleEntity

import com.crosstime.backend.repository.ScheduleRepository
import com.crosstime.backend.request.ScheduleRequest
import spock.lang.Specification

import java.time.LocalTime

class ScheduleServiceImplSpec extends Specification {

    private ScheduleMapper schedulerMapper = Mock(ScheduleMapper.class)
    private ScheduleRepository scheduleRepository = Mock(ScheduleRepository.class)

    private ScheduleServiceImpl scheduleService

    private static final UUID ID = UUID.randomUUID()

    def setup() {
        scheduleService = new ScheduleServiceImpl(schedulerMapper, scheduleRepository)
    }

    def "should add a new schedule"() {
        given: "a new schedule model"
        def request = buildScheduleRequest()

        when: "the method is called to add a new schedule"
        def result = scheduleService.addSchedule(request)

        then: "the repository is invoked"
        1 * scheduleRepository.save(_)
    }

    def "should return a list of schedules"() {
        given: "the expected schedules to be returned"
        def scheduleEntity = buildScheduleEntity()

        when: "the method is called to get all schedules"
        def result = scheduleService.getAllSchedules()

        then: "the repository is invoked"
        1 * scheduleRepository.findAll() >> { [scheduleEntity] }

        then: "the result is mapped to a list of schedules"
        1 * schedulerMapper.mapEntitiesToModels([scheduleEntity]) >> { Arrays.asList(buildScheduleModel()) }
    }

    def buildScheduleRequest() {
        return new ScheduleRequest(DayOfWeek.MONDAY, LocalTime.parse("00:00:00"))
    }

    def buildScheduleModel() {
        return new ScheduleModel(ID, DayOfWeek.MONDAY, LocalTime.parse("00:00:00"))
    }

    def buildScheduleEntity() {
        return new ScheduleEntity(ID, DayOfWeek.MONDAY, LocalTime.parse("00:00:00"))
    }

}
