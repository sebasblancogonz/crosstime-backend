package com.crosstime.backend.controller

import com.crosstime.backend.entity.Role
import com.crosstime.backend.request.AuthenticationRequest
import com.crosstime.backend.request.RegisterRequest
import com.crosstime.backend.response.AuthenticationResponse
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

import static com.crosstime.backend.utils.ScriptPathConstants.CLEAN_TOKENS
import static com.crosstime.backend.utils.ScriptPathConstants.CLEAN_USERS
import static com.crosstime.backend.utils.ScriptPathConstants.INIT_TOKENS
import static com.crosstime.backend.utils.ScriptPathConstants.INIT_USERS

@Title("The auth controller test class")
@Narrative("This class will test only the happy path for each rest operation over the auth api")
@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = [INIT_USERS, INIT_TOKENS], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = [CLEAN_TOKENS, CLEAN_USERS], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AuthControllerSpec extends Specification {

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    MockMvc mockMvc

    def "should return a response entity with the user tokens"() {
        given: "A request to register a user"
        def registerRequest = new RegisterRequest("test", "test", "test@test.com", Role.USER, null)
        when: "The method is called"
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()

        then: "The response entity contains the user information"
        def authenticationResponse = objectMapper.readValue(response.response.getContentAsString(), AuthenticationResponse)
        assert authenticationResponse.accessToken != null
        assert authenticationResponse.refreshToken != null
    }

    def "should return a response entity with the user tokens when authenticating"() {
        given: "A request to authenticate a user"
        def registerRequest = new AuthenticationRequest("email@email.com", "hellokitty")

        when: "The method is called"
        def response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjk5OTk5OTk5OTk5OTk5LCJleHAiOjk5OTk5OTk5OTk5OTk5fQ.f2KMM65Zqq4urAVBER31Mqa3gk4W9XfCB1sJASg_0pE")
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()

        then: "The response entity contains the user information"
        def authenticationResponse = objectMapper.readValue(response.response.getContentAsString(), AuthenticationResponse)
        assert authenticationResponse.accessToken != null
        assert authenticationResponse.refreshToken != null
    }

    def "should refresh the user tokens"() {
        given: "A request to refresh the user tokens"
        expect: "The method is called and returns 200 OK"
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/refresh-token")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjk5OTk5OTk5OTk5OTk5LCJleHAiOjk5OTk5OTk5OTk5OTk5fQ.f2KMM65Zqq4urAVBER31Mqa3gk4W9XfCB1sJASg_0pE"))
                .andExpect(MockMvcResultMatchers.status().isOk())
    }

    def "should logout the user"() {
        given: "A request to logout the user"
        expect: "The method is called and returns 200 OK"
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjk5OTk5OTk5OTk5OTk5LCJleHAiOjk5OTk5OTk5OTk5OTk5fQ.f2KMM65Zqq4urAVBER31Mqa3gk4W9XfCB1sJASg_0pE"))
                .andExpect(MockMvcResultMatchers.status().isOk())
    }

}