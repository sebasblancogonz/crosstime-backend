package com.crosstime.backend.controller

import com.crosstime.backend.model.User
import com.crosstime.backend.request.UserRequest
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

@Title("The users controller test class")
@Narrative("This class will test only the happy path and the sad path for each rest operation over the users api")
@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/db/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/db/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UsersControllerTest extends Specification {

    private static A_UUID = '5fcab368-b148-41fe-a0ee-91fb6b5a63ee'

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper objectMapper

    def "should create a user"() {
        given: "A create user request"
        def userRequest = new UserRequest("username", "email")
        expect: "Status is 200 and the response is the user's generated ID"
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
    }

    def "should retrieve a user given an ID"() {
        given: "A created user"
        def expectedUser = new User(UUID.fromString(A_UUID), "Username", "email@email.com")
        when: "The get user by id endpoint is called"
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{userId}", A_UUID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()

        def user = objectMapper.readValue(response.response.getContentAsString(), User.class)

        then: "The information matches"
        assert expectedUser.email == user.email
        assert expectedUser.username == user.username
        assert expectedUser.id == user.id
    }

    def "should retrieve a list of users"() {
        given: "A created user"
        def expectedUser = [new User(UUID.fromString(A_UUID), "Username", "email@email.com")]

        when: "The get user by id endpoint is called"
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()

        def users = objectMapper.readValue(response.response.getContentAsString(), new TypeReference<List<User>>() {})

        then: "The information matches"
        assert users.size() == 1
        assert expectedUser.first().email == users.first().email
        assert expectedUser.first().username == users.first().username
    }

}
