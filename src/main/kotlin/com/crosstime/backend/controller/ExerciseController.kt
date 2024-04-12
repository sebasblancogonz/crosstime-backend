package com.crosstime.backend.controller

import com.crosstime.backend.model.Exercise
import com.crosstime.backend.model.User
import com.crosstime.backend.request.UserRequest
import com.crosstime.backend.response.UserResponse
import com.crosstime.backend.service.ExerciseService
import com.crosstime.backend.service.UsersService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/exercises")
class ExerciseController(
    val exerciseService: ExerciseService
) {

    @GetMapping
    fun getAllExercises(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<Exercise>> {
        val pageable = PageRequest.of(page, size)
        val exercisesPage = exerciseService.findAllExercises(pageable)
        return ResponseEntity.ok(exercisesPage)
    }

}