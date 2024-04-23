package com.crosstime.backend.service

import com.crosstime.backend.model.Exercise
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

fun interface ExerciseService {

    fun findAllExercises(pageable: Pageable): Page<Exercise>

}