package com.crosstime.backend.mapper

import com.crosstime.backend.entity.Exercise as ExerciseEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import spock.lang.Specification

@SpringBootTest(classes = TestConfig)
class ExerciseMapperSpec extends Specification {

    @Autowired
    ExerciseMapper exerciseMapper

    def "should map an exercise entity to a model"() {
        given: "An exercise entity"
        def exerciseEntity = new ExerciseEntity(1, "3/4 Sit-Up", "pull", "beginner", "compound", "body only", "[\"abdominals\"]", "[]", "[\"Lie down on the floor and secure your feet. Your legs should be bent at the knees.\",\"Place your hands behind or to the side of your head. You will begin with your back on the ground. This will be your starting position.\",\"Flex your hips and spine to raise your torso toward your knees.\",\"At the top of the contraction your torso should be perpendicular to the ground. Reverse the motion, going only ¾ of the way down.\",\"Repeat for the recommended amount of repetitions.\"]", "strength", "[\"3_4_Sit-Up/0.jpg\",\"3_4_Sit-Up/1.jpg\"]", "3_4_Sit-Up")
        when: "The entity is mapped to a model"
        def exerciseModel = exerciseMapper.toModel(exerciseEntity, "https://test-url.test/")
        then: "The model has the same values as the entity"
        assert exerciseEntity.id == exerciseModel.id
        assert exerciseEntity.name == exerciseModel.name
        assert exerciseEntity.category == exerciseModel.category
        assert exerciseEntity.getPrimaryMuscles() == exerciseModel.primaryMuscles
        assert exerciseEntity.getSecondaryMuscles() == exerciseModel.secondaryMuscles
        assert exerciseEntity.getInstructions() == exerciseModel.instructions
        exerciseEntity.images.forEach { image -> assert exerciseModel.images.contains("https://test-url.test/" + image) }
    }

    @Configuration
    @ComponentScan(basePackages = ["com.crosstime.backend.mapper"])
    static class TestConfig {

        @Bean
        ExerciseMapperDecorator exerciseMapperDecorator() {
            return new ExerciseMapperDecorator()
        }
    }
}
