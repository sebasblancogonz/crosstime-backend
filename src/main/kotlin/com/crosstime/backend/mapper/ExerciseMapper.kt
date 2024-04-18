package com.crosstime.backend.mapper

import org.mapstruct.Context
import org.mapstruct.DecoratedWith
import org.mapstruct.Mapper
import com.crosstime.backend.entity.Exercise as ExerciseEntity
import com.crosstime.backend.model.Exercise as ExerciseModel

@Mapper
@DecoratedWith(ExerciseMapperDecorator::class)
interface ExerciseMapper {

    fun toModel(exercise: ExerciseEntity, @Context imagesUrl: String): ExerciseModel

}