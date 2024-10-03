package com.crosstime.backend.controller

import com.crosstime.backend.model.Reservation
import com.crosstime.backend.repository.ReservationRepository
import com.crosstime.backend.request.ReservationRequest
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

import static com.crosstime.backend.utils.ScriptPathConstants.CLEAN_RESERVATIONS
import static com.crosstime.backend.utils.ScriptPathConstants.CLEAN_SCHEDULES
import static com.crosstime.backend.utils.ScriptPathConstants.CLEAN_SLOTS
import static com.crosstime.backend.utils.ScriptPathConstants.CLEAN_TOKENS
import static com.crosstime.backend.utils.ScriptPathConstants.CLEAN_USERS
import static com.crosstime.backend.utils.ScriptPathConstants.INIT_RESERVATIONS
import static com.crosstime.backend.utils.ScriptPathConstants.INIT_SCHEDULES
import static com.crosstime.backend.utils.ScriptPathConstants.INIT_SLOTS
import static com.crosstime.backend.utils.ScriptPathConstants.INIT_TOKENS
import static com.crosstime.backend.utils.ScriptPathConstants.INIT_USERS

@Title("The reservation controller test class")
@Narrative("This class will test only the happy path for each rest operation over the reservations api")
@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = [INIT_SCHEDULES, INIT_SLOTS], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = [INIT_USERS, INIT_TOKENS, INIT_RESERVATIONS], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = [CLEAN_TOKENS, CLEAN_USERS, CLEAN_RESERVATIONS], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = [CLEAN_SLOTS, CLEAN_SCHEDULES], executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class ReservationControllerSpec extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper objectMapper

    @Autowired
    private ReservationRepository reservationRepository

    def "should create a reservation"() {
        given: "A request create a reservation"
        def reservationRequest = new ReservationRequest(
                UUID.fromString("5fcab368-b148-41fe-a0ee-91fb6b5a63ee"),
                UUID.fromString("5fcab368-b148-41fe-a0ee-91fb6b5a63ee")
        )

        reservationRepository.delete(reservationRepository.findAll().get(0))
        when: "The create reservation endpoint is called"
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservationRequest))
                .header("authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjk5OTk5OTk5OTk5OTk5LCJleHAiOjk5OTk5OTk5OTk5OTk5fQ.f2KMM65Zqq4urAVBER31Mqa3gk4W9XfCB1sJASg_0pE"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
        then: "The reservation is created"
        assert reservationRepository.findAll().size() == 1
    }

    def "should return a reservation given a slot ID"() {
        given: "A request to get a reservation by a slot ID"
        when: "The get reservation by slot ID endpoint is called"
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/api/reservations/slot-id/{slotId}", "5fcab368-b148-41fe-a0ee-91fb6b5a63ee")
                .header("authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjk5OTk5OTk5OTk5OTk5LCJleHAiOjk5OTk5OTk5OTk5OTk5fQ.f2KMM65Zqq4urAVBER31Mqa3gk4W9XfCB1sJASg_0pE"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
        then: "The response is a reservation"
        def reservation = objectMapper.readValue(response.response.getContentAsString(), new TypeReference<List<Reservation>>() {})
        assert reservation[0].id == UUID.fromString("5fcab368-b148-41fe-a0ee-91fb6b5a63ee")
    }

    def "should return a reservation given a user ID"() {
        given: "A request to get a reservation by a user ID"
        when: "The get reservation by user ID endpoint is called"
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/api/reservations/user-id/{userId}", "5fcab368-b148-41fe-a0ee-91fb6b5a63ee")
                .header("authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjk5OTk5OTk5OTk5OTk5LCJleHAiOjk5OTk5OTk5OTk5OTk5fQ.f2KMM65Zqq4urAVBER31Mqa3gk4W9XfCB1sJASg_0pE"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
        then: "The response is a reservation"
        def reservation = objectMapper.readValue(response.response.getContentAsString(), new TypeReference<List<Reservation>>() {})
        assert reservation[0].id == UUID.fromString("5fcab368-b148-41fe-a0ee-91fb6b5a63ee")
    }


}
