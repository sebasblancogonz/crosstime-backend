package com.crosstime.backend.service

import com.crosstime.backend.model.Exercise

interface ExerciseService {

    fun findAllExercises(): List<Exercise>

}