package com.crosstime.backend.controller

import com.crosstime.backend.model.Exercise
import com.crosstime.backend.service.ExerciseService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/exercises")
class ExerciseController(
    val exerciseService: ExerciseService
) {

    @GetMapping
    fun getAllExercises(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(value = "forceType", required = false) forceType: String?,
        @RequestParam(value = "primaryMuscle", required = false) primaryMuscle: String?,
        @RequestParam(value = "secondaryMuscle", required = false) secondaryMuscle: String?,
        @RequestParam(value = "level", required = false) level: String?
    ): ResponseEntity<Page<Exercise>> {
        val pageable = PageRequest.of(page, size)
        val filters = mutableMapOf<String, String?>()
        forceType?.let { filters["forceType"] = it }
        primaryMuscle?.let { filters["primaryMuscle"] = it }
        secondaryMuscle?.let { filters["secondaryMuscle"] = it }
        level?.let { filters["level"] = it }
        val exercisesPage = exerciseService.findAllExercises(pageable)
        return ResponseEntity.ok(exercisesPage)
    }

}