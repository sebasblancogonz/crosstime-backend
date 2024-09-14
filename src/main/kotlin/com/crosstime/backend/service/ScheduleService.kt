package com.crosstime.backend.service

import com.crosstime.backend.model.Schedule
import com.crosstime.backend.request.ScheduleRequest

interface ScheduleService {

    fun addSchedule(request: ScheduleRequest)

    fun getAllSchedules(): List<Schedule>

}