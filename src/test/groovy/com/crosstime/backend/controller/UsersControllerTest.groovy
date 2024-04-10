package com.crosstime.backend.controller

import com.crosstime.backend.request.UserRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

@Title("The users controller test class")
@Narrative("This class will test only the happy path and the sad path for each rest operation over the users api")
@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper objectMapper

    def "should return users"() {
        given: "A create user request"
        def userRequest = new UserRequest("username", "email")
        expect: "Status is 200 and the response is the hardcoded user"
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
    }

}
