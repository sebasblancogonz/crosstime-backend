package com.crosstime.backend.repository

import com.crosstime.backend.entity.Schedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ScheduleRepository : JpaRepository<Schedule, UUID>