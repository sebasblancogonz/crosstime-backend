package com.crosstime.backend.repository

import com.crosstime.backend.entity.ScheduleException
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ScheduleExceptionRepository : JpaRepository<ScheduleException, UUID>