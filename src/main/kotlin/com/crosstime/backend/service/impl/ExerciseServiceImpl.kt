package com.crosstime.backend.service.impl

import com.crosstime.backend.mapper.ExerciseMapper
import com.crosstime.backend.model.Exercise
import com.crosstime.backend.repository.ExerciseRepository
import com.crosstime.backend.service.ExerciseService
import org.springframework.stereotype.Service

@Service
class ExerciseServiceImpl(
    val exerciseRepository: ExerciseRepository,
    val exerciseMapper: ExerciseMapper
) : ExerciseService {

    override fun findAllExercises(): List<Exercise> {
        val exerciseEntities = exerciseRepository.findAll()
        exerciseEntities.ifEmpty {
            return emptyList()
        }
        return exerciseMapper.mapToModelList(exerciseEntities)
    }

}