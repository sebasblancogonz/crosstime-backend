package com.crosstime.backend.service.impl

import com.crosstime.backend.mapper.ExerciseMapper
import com.crosstime.backend.model.Exercise
import com.crosstime.backend.repository.ExerciseRepository
import com.crosstime.backend.service.ExerciseService
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ExerciseServiceImpl(
    val exerciseRepository: ExerciseRepository,
    val exerciseMapper: ExerciseMapper
) : ExerciseService {

    @Cacheable("exercises")
    override fun findAllExercises(pageable: Pageable): Page<Exercise> {
        val exerciseEntitiesPage = exerciseRepository.findAll(pageable);
        return exerciseEntitiesPage.map { exerciseMapper.toModel(it) }
    }

}