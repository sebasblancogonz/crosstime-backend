package com.crosstime.backend.mapper

import com.crosstime.backend.model.Exercise as ExerciseModel
import com.crosstime.backend.entity.Exercise as ExerciseEntity
import org.mapstruct.Mapper

@Mapper
interface ExerciseMapper {

    fun toModel(exercise: ExerciseEntity): ExerciseModel
    fun mapToModelList(exercise: List<ExerciseEntity>): List<ExerciseModel>

}