package com.crosstime.backend.controller

import com.crosstime.backend.entity.Role
import com.crosstime.backend.enums.UserType
import com.crosstime.backend.model.AuthenticatedUser
import com.crosstime.backend.model.User
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

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/db/users/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/db/tokens/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/db/tokens/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/db/users/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Title("The users controller test class")
@Narrative("This class will test only the happy path for each rest operation over the users api")
class UsersControllerSpec extends Specification {

    private static A_UUID = '5fcab368-b148-41fe-a0ee-91fb6b5a63ee'

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper objectMapper

    def "should retrieve a user given an ID"() {
        given: "A created user"
        def expectedUser = new AuthenticatedUser("\$2a\$10\$edNFyg9/dWIJ4a.X3Vo6A.4wS3k.1.iQ7b6ysm2NkJ8hSdWWocBCu", Role.USER, UUID.fromString(A_UUID), "Username", "email@email.com", UserType.ATHLETE)
        when: "The get user by id endpoint is called"
        def response = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/{userId}", A_UUID)
                        .header("authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjk5OTk5OTk5OTk5OTk5LCJleHAiOjk5OTk5OTk5OTk5OTk5fQ.f2KMM65Zqq4urAVBER31Mqa3gk4W9XfCB1sJASg_0pE"))
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
        def expectedUser = [new AuthenticatedUser("\$2a\$10\$edNFyg9/dWIJ4a.X3Vo6A.4wS3k.1.iQ7b6ysm2NkJ8hSdWWocBCu", Role.USER, UUID.fromString(A_UUID), "Username", "email@email.com", UserType.ATHLETE)]

        when: "The get user by id endpoint is called"
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                .header("authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjk5OTk5OTk5OTk5OTk5LCJleHAiOjk5OTk5OTk5OTk5OTk5fQ.f2KMM65Zqq4urAVBER31Mqa3gk4W9XfCB1sJASg_0pE"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()

        def users = objectMapper.readValue(response.response.getContentAsString(), new TypeReference<List<User>>() {})

        then: "The information matches"
        assert users.size() == 1
        assert expectedUser.first().email == users.first().email
        assert expectedUser.first().username == users.first().username
    }

}
