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

import static com.crosstime.backend.utils.ScriptPathConstants.CLEAN_EXERCISES
import static com.crosstime.backend.utils.ScriptPathConstants.CLEAN_TOKENS
import static com.crosstime.backend.utils.ScriptPathConstants.CLEAN_USERS
import static com.crosstime.backend.utils.ScriptPathConstants.INIT_EXERCISES
import static com.crosstime.backend.utils.ScriptPathConstants.INIT_TOKENS
import static com.crosstime.backend.utils.ScriptPathConstants.INIT_USERS

@Title("The exercise controller test class")
@Narrative("This class will test only the happy path for each rest operation over the exercise api")
@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = [INIT_USERS, INIT_TOKENS, INIT_EXERCISES], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = [CLEAN_TOKENS, CLEAN_USERS], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = [CLEAN_EXERCISES], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class ExerciseControllerSpec extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper objectMapper

    def "should return a list of exercises"() {
        given: "A request to get all exercises"
        when: "The get all exercises endpoint is called"
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/api/exercises")
                .header("authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjk5OTk5OTk5OTk5OTk5LCJleHAiOjk5OTk5OTk5OTk5OTk5fQ.f2KMM65Zqq4urAVBER31Mqa3gk4W9XfCB1sJASg_0pE"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
        then: "The response is a list of exercises"
        def page = objectMapper.readValue(response.response.getContentAsString(), Map)
        def exercises = objectMapper.convertValue(page.get("content"), new TypeReference<List<Exercise>>() {})
        assert exercises.size() == 2
    }

}
