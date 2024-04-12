package com.crosstime.backend.service

import com.crosstime.backend.model.Exercise
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ExerciseService {

    fun findAllExercises(pageable: Pageable): Page<Exercise>

}