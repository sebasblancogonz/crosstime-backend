package com.crosstime.backend.service.impl

import com.crosstime.backend.entity.Exercise as ExerciseEntity
import com.crosstime.backend.mapper.ExerciseMapper
import com.crosstime.backend.model.Exercise as ExerciseModel
import com.crosstime.backend.repository.ExerciseRepository
import spock.lang.Specification

class ExerciseServiceImplSpec extends Specification {

    private static final UUID constUuid = UUID.randomUUID()
    private ExerciseMapper exerciseMapper = Mock(ExerciseMapper.class)
    private ExerciseRepository exerciseRepository = Mock(ExerciseRepository.class)
    private ExerciseServiceImpl exerciseService

    def setup() {
        exerciseService = new ExerciseServiceImpl(exerciseRepository, exerciseMapper)
    }

    def "should return all the users"() {
        given: "an expected list of users to be returned"
        def expectedExerciseEntities = [new ExerciseEntity(1, "3/4 Sit-Up","pull","beginner","compound","body only","[\"abdominals\"]","[]","[\"Lie down on the floor and secure your feet. Your legs should be bent at the knees.\",\"Place your hands behind or to the side of your head. You will begin with your back on the ground. This will be your starting position.\",\"Flex your hips and spine to raise your torso toward your knees.\",\"At the top of the contraction your torso should be perpendicular to the ground. Reverse the motion, going only ¾ of the way down.\",\"Repeat for the recommended amount of repetitions.\"]","strength","[\"3_4_Sit-Up/0.jpg\",\"3_4_Sit-Up/1.jpg\"]","3_4_Sit-Up")]
        def expectedExerciseModels = [new ExerciseModel(1, "3/4 Sit-Up","pull","beginner","compound","body only",["abdominals"], [],["Lie down on the floor and secure your feet. Your legs should be bent at the knees.","Place your hands behind or to the side of your head. You will begin with your back on the ground. This will be your starting position.","Flex your hips and spine to raise your torso toward your knees.","At the top of the contraction your torso should be perpendicular to the ground. Reverse the motion, going only ¾ of the way down.","Repeat for the recommended amount of repetitions."],"strength",["3_4_Sit-Up/0.jpg","3_4_Sit-Up/1.jpg"],"3_4_Sit-Up")]

        and: "the repository is invoked"
        1 * exerciseRepository.findAll() >> { expectedExerciseEntities }

        and: "the mapper is invoked"
        1 * exerciseMapper.mapToModelList(expectedExerciseEntities) >> expectedExerciseModels

        when: "the method is called to return the user"
        def result = exerciseService.findAllExercises()


        then: "the returned user is the correct one"
        assert result.size() == 1
    }

    def "should return an empty list of users"() {
        given: "an expected empty list"
        def emptyList = []

        and: "the repository is invoked"
        1 * exerciseRepository.findAll() >> { emptyList }

        and: "the mapper is invoked"
        0 * exerciseMapper.mapToModelList(_)

        when: "the method is called to return the user"
        def result = exerciseService.findAllExercises()


        then: "the returned user is the correct one"
        assert result.size() == 0
    }

}
