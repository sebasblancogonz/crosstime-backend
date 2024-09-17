package com.crosstime.backend.service.impl

import com.crosstime.backend.mapper.ScheduleMapper
import com.crosstime.backend.model.Schedule as ScheduleModel
import com.crosstime.backend.entity.Schedule as ScheduleEntity
import com.crosstime.backend.repository.ScheduleRepository
import com.crosstime.backend.request.ScheduleRequest
import com.crosstime.backend.service.ScheduleService
import org.springframework.stereotype.Service

@Service
class ScheduleServiceImpl(
    val scheduleMapper: ScheduleMapper,
    val scheduleRepository: ScheduleRepository
) : ScheduleService {

    override fun addSchedule(request: ScheduleRequest) {
        val schedule = ScheduleEntity(
            dayOfWeek = request.dayOfWeek,
            timeOfDay = request.timeOfDay
        )

        scheduleRepository.save(schedule)
    }

    override fun getAllSchedules(): List<ScheduleModel> {
        return scheduleMapper.mapEntitiesToModels(scheduleRepository.findAll())
    }

}