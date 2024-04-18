package com.crosstime.backend.mapper

import org.mapstruct.Context
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import com.crosstime.backend.entity.Exercise as ExerciseEntity
import com.crosstime.backend.model.Exercise as ExerciseModel

open class ExerciseMapperDecorator : ExerciseMapper {

    @Autowired
    @Qualifier("delegate")
    private lateinit var delegate: ExerciseMapper

    override fun toModel(exercise: ExerciseEntity, @Context imagesUrl: String): ExerciseModel {
        val model = delegate.toModel(exercise, imagesUrl)
        model.images = model.images.map { imagesUrl + it }
        return model
    }
}