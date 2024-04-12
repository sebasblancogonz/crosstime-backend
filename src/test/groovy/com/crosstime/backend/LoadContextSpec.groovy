package com.crosstime.backend

import com.crosstime.backend.controller.ExerciseController
import com.crosstime.backend.controller.UsersController
import com.crosstime.backend.mapper.ExerciseMapper
import com.crosstime.backend.mapper.UsersMapper
import com.crosstime.backend.repository.ExerciseRepository
import com.crosstime.backend.repository.UsersRepository
import com.crosstime.backend.service.ExerciseService
import com.crosstime.backend.service.UsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class LoadContextSpec extends Specification {

    @Autowired(required = false)
    private UsersController usersController

    @Autowired(required = false)
    private UsersService usersService

    @Autowired(required = false)
    private UsersMapper usersMapper

    @Autowired(required = false)
    private UsersRepository usersRepository

    @Autowired(required = false)
    private ExerciseController exerciseController

    @Autowired(required = false)
    private ExerciseService exerciseService

    @Autowired
    private ExerciseMapper exerciseMapper

    @Autowired
    private ExerciseRepository exerciseRepository

    def "all beans should be created when context is loaded"() {
        expect: "usersController is created"
        usersController
        and: "usersService is created"
        usersService
        and: "usersMapper is created"
        usersMapper
        and: "usersRepository is created"
        usersRepository
        and: "exerciseController is created"
        exerciseController
        and: "exerciseService is created"
        exerciseService
        and: "exerciseMapper is created"
        exerciseMapper
        and: "exerciseRepository is created"
        exerciseRepository
    }

}
