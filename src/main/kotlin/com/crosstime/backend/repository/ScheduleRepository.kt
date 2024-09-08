package com.crosstime.backend.repository

import com.crosstime.backend.entity.Schedule
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ScheduleRepository : JpaRepository<Schedule, UUID>