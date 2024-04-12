package com.crosstime.backend.controller

import com.crosstime.backend.model.Exercise
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

@Title("The exercise controller test class")
@Narrative("This class will test only the happy path for each rest operation over the exercise api")
@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/db/exercises/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/db/exercises/clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/db/exercises/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ExerciseControllerSpec extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper objectMapper

    def "should return a list of exercises"() {
        given: "A request to get all exercises"
        when: "The get all exercises endpoint is called"
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/api/exercises"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
        then: "The response is a list of exercises"
        def exercises = objectMapper.readValue(response.response.getContentAsString(), new TypeReference<List<Exercise>>() {})
        assert exercises.size() == 2
    }

}
