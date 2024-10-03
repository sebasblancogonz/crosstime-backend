package com.crosstime.backend.controller

import com.crosstime.backend.enums.TrainingType
import com.crosstime.backend.model.Slot
import com.crosstime.backend.repository.SlotRepository
import com.crosstime.backend.request.SlotRequest
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.LocalDateTime
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
import static com.crosstime.backend.utils.ScriptPathConstants.CLEAN_SLOTS
import static com.crosstime.backend.utils.ScriptPathConstants.CLEAN_TOKENS
import static com.crosstime.backend.utils.ScriptPathConstants.CLEAN_USERS
import static com.crosstime.backend.utils.ScriptPathConstants.INIT_SCHEDULES
import static com.crosstime.backend.utils.ScriptPathConstants.INIT_SLOTS
import static com.crosstime.backend.utils.ScriptPathConstants.INIT_TOKENS
import static com.crosstime.backend.utils.ScriptPathConstants.INIT_USERS

@Title("The slot controller test class")
@Narrative("This class will test only the happy path for each rest operation over the slot api")
@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = [INIT_SCHEDULES, INIT_SLOTS], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = [INIT_USERS, INIT_TOKENS], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = [CLEAN_TOKENS, CLEAN_USERS], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = [CLEAN_SLOTS, CLEAN_SCHEDULES], executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class SlotControllerSpec extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper objectMapper

    @Autowired
    private SlotRepository slotRepository

    def "should create a slot"() {
        given: "A request to create a slot"
        def request = new SlotRequest(
                40,
                LocalDateTime.of(2021, 1, 1, 10, 0),
                40,
                TrainingType.WOD
        )

        when: "The create slot endpoint is called"
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/api/slots/create")
                .header("authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjk5OTk5OTk5OTk5OTk5LCJleHAiOjk5OTk5OTk5OTk5OTk5fQ.f2KMM65Zqq4urAVBER31Mqa3gk4W9XfCB1sJASg_0pE")
                .content(objectMapper.writeValueAsString(request))
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()

        then: "The slot is stored in database"
        assert slotRepository.findAll().size() == 2
    }

    def "should return a list of slots"() {
        given: "A request to get all slots"
        when: "The get all slots endpoint is called"
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/api/slots")
                .header("authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjk5OTk5OTk5OTk5OTk5LCJleHAiOjk5OTk5OTk5OTk5OTk5fQ.f2KMM65Zqq4urAVBER31Mqa3gk4W9XfCB1sJASg_0pE"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
        then: "The response is a list of exercises"
        def slots = objectMapper.readValue(response.response.getContentAsString(), new TypeReference<List<Slot>>() {})
        assert slots.size() == 2
    }

    def "should return a slot given an ID"() {
        given: "A request to get a slot by ID"
        when: "The get slot by ID endpoint is called"
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/api/slots/{slotId}", "5fcab368-b148-41fe-a0ee-91fb6b5a63ee")
                .header("authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjk5OTk5OTk5OTk5OTk5LCJleHAiOjk5OTk5OTk5OTk5OTk5fQ.f2KMM65Zqq4urAVBER31Mqa3gk4W9XfCB1sJASg_0pE"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
        then: "The response is a slot"
        def slot = objectMapper.readValue(response.response.getContentAsString(), Slot.class)
        assert slot.id == UUID.fromString("5fcab368-b148-41fe-a0ee-91fb6b5a63ee")
    }

}
