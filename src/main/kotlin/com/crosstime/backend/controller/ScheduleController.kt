package com.crosstime.backend.controller

import com.crosstime.backend.model.Schedule
import com.crosstime.backend.request.ScheduleRequest
import com.crosstime.backend.service.ScheduleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/schedules")
class ScheduleController(
    val scheduleService: ScheduleService
) {

    @PostMapping("/create")
    fun createSchedule(@RequestBody request: ScheduleRequest): ResponseEntity<Unit> {
        scheduleService.addSchedule(request)
        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun getAllSchedules(): ResponseEntity<List<Schedule>> =
        ResponseEntity.ok(scheduleService.getAllSchedules())

}