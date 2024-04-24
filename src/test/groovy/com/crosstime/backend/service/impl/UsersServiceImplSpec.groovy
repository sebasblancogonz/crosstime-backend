package com.crosstime.backend.service.impl

import com.crosstime.backend.entity.Role
import com.crosstime.backend.entity.User as UserEntity
import com.crosstime.backend.exeption.UserNotFoundException
import com.crosstime.backend.mapper.UsersMapper
import com.crosstime.backend.model.User as UserModel
import com.crosstime.backend.repository.UsersRepository
import spock.lang.Specification

class UsersServiceImplSpec extends Specification {

    private static final UUID constUuid = UUID.randomUUID()
    private UsersMapper usersMapper = Mock(UsersMapper.class)
    private UsersRepository usersRepository = Mock(UsersRepository.class)
    private UsersServiceImpl usersService

    def setup() {
        usersService = new UsersServiceImpl(usersRepository, usersMapper)
    }

    def "should return a specific user"() {
        given: "an expected user to be returned"
        def expectedUserEntity = new UserEntity(constUuid, "username", "email@email.com", "password", Role.USER)
        def expectedUserModel = new UserModel(constUuid, "username", "email@email.com", "password", Role.USER)

        when: "the method is called to return the user"
        def result = usersService.getUserById(constUuid)

        then: "the repository is invoked"
        1 * usersRepository.findById(constUuid) >> { Optional.of(expectedUserEntity) }

        and: "the mapper is invoked"
        1 * usersMapper.mapToModel(expectedUserEntity) >> expectedUserModel

        then: "the returned user is the correct one"
        assert expectedUserEntity.id == result.id
        assert expectedUserEntity.email == result.email
        assert expectedUserEntity.alias == result.username
    }

    def "shouldn't return a user"() {
        given: "the repository is invoked"
        1 * usersRepository.findById(constUuid) >> { Optional.empty() }

        and: "the mapper is invoked"
        0 * usersMapper.mapToModel(_)

        when: "the method is called to return the user"
        usersService.getUserById(constUuid)

        then: "the returned user is null"
        thrown(UserNotFoundException)
    }

    def "should return all the users"() {
        given: "an expected list of users to be returned"
        def expectedUserEntities = [new UserEntity(constUuid, "username", "email@email.com", "password", Role.USER)]
        def expectedUserModelList = [new UserModel(constUuid, "username", "email@email.com", "password", Role.USER)]

        when: "the method is called to return the user"
        def result = usersService.findAllUsers()

        then: "the repository is invoked"
        1 * usersRepository.findAll() >> { expectedUserEntities }

        and: "the mapper is invoked"
        1 * usersMapper.mapToModelList(expectedUserEntities) >> expectedUserModelList

        and: "the returned user is the correct one"
        assert result.size() == 1
    }

    def "should return an empty list of users"() {
        given: "an expected empty list"
        def emptyList = []

        when: "the method is called to return the user"
        def result = usersService.findAllUsers()

        then: "the repository is invoked"
        1 * usersRepository.findAll() >> { emptyList }

        and: "the mapper is invoked"
        0 * usersMapper.mapToModelList(_)


        and: "the returned user is the correct one"
        assert result.size() == 0
    }

    def "should return a user by email"() {
        given: "an expected user to be returned"
        def expectedUserEntity = new UserEntity(constUuid, "username", "email@email.com", "password", Role.USER)

        when: "the method is called to return the user"
        def result = usersService.loadUserByUsername("email@email.com")

        then: "the repository is invoked"
        1 * usersRepository.findByEmail("email@email.com") >> expectedUserEntity

        then: "the returned user is the correct one"
        assert expectedUserEntity.email == result.username
    }

    def "shouldn't return a user by email"() {
        given: "the repository is invoked"
        1 * usersRepository.findByEmail("email@email.com") >> null

        when: "the method is called to return the user"
        usersService.loadUserByUsername("email@email.com")

        then: "an exception is thrown"
        def exception = thrown(UserNotFoundException)
        assert exception.message == "User with email email@email.com not found."
    }


}
