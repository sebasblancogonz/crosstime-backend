package com.crosstime.backend.service.impl

import com.crosstime.backend.model.User
import spock.lang.Specification

class UsersServiceImplTest extends Specification {

    private UsersServiceImpl usersService

    def setup() {
        usersService = new UsersServiceImpl()
    }

    def "should return a hardcoded user"() {
        given: "an expected user"
        def uuid = UUID.randomUUID()
        def expectedUser = new User(uuid, "Username", "email@email.com", "password")

        when: "the create user method is called"
        def user = usersService.createUser()

        then: "the created user should be the expected one"
        assert expectedUser.email == user.email
        assert expectedUser.password == user.password
        assert expectedUser.username == user.username
    }

}
