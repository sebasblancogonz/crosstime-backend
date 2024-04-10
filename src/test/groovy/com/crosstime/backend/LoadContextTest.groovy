package com.crosstime.backend

import com.crosstime.backend.controller.UsersController
import com.crosstime.backend.mapper.UsersMapper
import com.crosstime.backend.repository.UsersRepository
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

    @Autowired(required = false)
    private UsersMapper usersMapper

    @Autowired(required = false)
    private UsersRepository usersRepository

    def "all beans should be created when context is loaded"() {
        expect: "usersController is created"
        usersController
        and: "usersService is created"
        usersService
        and: "usersMapper is created"
        usersMapper
        and: "usersRepository is created"
        usersRepository
    }

}
