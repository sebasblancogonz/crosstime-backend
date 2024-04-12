package com.crosstime.backend.mapper

import com.crosstime.backend.entity.Exercise as ExerciseEntity
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class ExerciseMapperSpec extends Specification {

    private ExerciseMapper exerciseMapper

    def setup() {
        exerciseMapper = Mappers.getMapper(ExerciseMapper.class)
    }

    def "should map an exercise entity to a model"() {
        given: "An exercise entity"
        def exerciseEntity = new ExerciseEntity(1, "3/4 Sit-Up","pull","beginner","compound","body only","[\"abdominals\"]","[]","[\"Lie down on the floor and secure your feet. Your legs should be bent at the knees.\",\"Place your hands behind or to the side of your head. You will begin with your back on the ground. This will be your starting position.\",\"Flex your hips and spine to raise your torso toward your knees.\",\"At the top of the contraction your torso should be perpendicular to the ground. Reverse the motion, going only ¾ of the way down.\",\"Repeat for the recommended amount of repetitions.\"]","strength","[\"3_4_Sit-Up/0.jpg\",\"3_4_Sit-Up/1.jpg\"]","3_4_Sit-Up")
        when: "The entity is mapped to a model"
        def exerciseModel = exerciseMapper.toModel(exerciseEntity)
        then: "The model has the same values as the entity"
        assert exerciseModel.id == exerciseEntity.id
        assert exerciseModel.name == exerciseEntity.name
        assert exerciseModel.category == exerciseEntity.category
        assert exerciseModel.primaryMuscles == exerciseEntity.getPrimaryMuscles()
        assert exerciseModel.secondaryMuscles == exerciseEntity.getSecondaryMuscles()
        assert exerciseModel.instructions == exerciseEntity.getInstructions()
        assert exerciseModel.images == exerciseEntity.getImages()
    }
    
}
