package com.crosstime.backend

import com.crosstime.backend.controller.UsersController
import com.crosstime.backend.service.UsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class LoadContextTest extends Specification {

    @Autowired(required = false)
    private UsersController usersController

    @Autowired(required = false)
    private UsersService usersService

    def "all beans should be created when context is loaded"() {
        expect: "usersController is created"
        usersController
        and: "usersService is created"
        usersService
    }

}
