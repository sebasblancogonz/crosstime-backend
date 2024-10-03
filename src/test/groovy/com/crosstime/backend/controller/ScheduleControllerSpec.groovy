package com.crosstime.backend.controller

import com.crosstime.backend.enums.DayOfWeek
import com.crosstime.backend.model.Schedule
import com.crosstime.backend.model.Slot
import com.crosstime.backend.repository.ScheduleRepository
import com.crosstime.backend.request.ScheduleRequest
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.LocalTime
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

import static com.crosstime.backend.utils.ScriptPathConstants.CLEAN_SCHEDULES
import static com.crosstime.backend.utils.ScriptPathConstants.CLEAN_TOKENS
import static com.crosstime.backend.utils.ScriptPathConstants.CLEAN_USERS
import static com.crosstime.backend.utils.ScriptPathConstants.INIT_SCHEDULES
import static com.crosstime.backend.utils.ScriptPathConstants.INIT_TOKENS
import static com.crosstime.backend.utils.ScriptPathConstants.INIT_USERS

@Title("The schedules controller test class")
@Narrative("This class will test only the happy path for each rest operation over the schedules api")
@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = [INIT_SCHEDULES], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = [INIT_USERS, INIT_TOKENS], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = [CLEAN_TOKENS, CLEAN_USERS], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = [CLEAN_SCHEDULES], executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class ScheduleControllerSpec extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper objectMapper

    @Autowired
    private ScheduleRepository scheduleRepository

    def "should create an schedule"() {
        given: "A request to create an schedule"
        def request = new ScheduleRequest(
                DayOfWeek.MONDAY,
                LocalTime.of(8, 0),
        )

        when: "The create schedule endpoint is called"
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/api/schedules/create")
                .header("authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjk5OTk5OTk5OTk5OTk5LCJleHAiOjk5OTk5OTk5OTk5OTk5fQ.f2KMM65Zqq4urAVBER31Mqa3gk4W9XfCB1sJASg_0pE")
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()

        then: "The schedule is stored in database"
        assert scheduleRepository.findAll().size() == 2
    }

    def "should return a list of schedules"() {
        given: "A request to get all schedules"
        when: "The get all schedules endpoint is called"
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/api/schedules")
                .header("authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjk5OTk5OTk5OTk5OTk5LCJleHAiOjk5OTk5OTk5OTk5OTk5fQ.f2KMM65Zqq4urAVBER31Mqa3gk4W9XfCB1sJASg_0pE"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
        then: "The response is a list of exercises"
        def slots = objectMapper.readValue(response.response.getContentAsString(), new TypeReference<List<Schedule>>() {})
        assert slots.size() == 2
    }

}
