package com.crosstime.backend.repository

import com.crosstime.backend.entity.Exercise
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ExerciseRepository : JpaRepository<Exercise, Long> {
    override fun findAll(pageable: Pageable): Page<Exercise>

}