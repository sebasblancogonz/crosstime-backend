package com.crosstime.backend.service.impl

import com.crosstime.backend.entity.User as UserEntity
import com.crosstime.backend.exeption.UserNotFoundException
import com.crosstime.backend.exeption.UserNotSavedException
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

    def "should store successfully the user and return the uuid"() {
        given: "a user to be created"
        def userToBeSaved = new UserModel(null, "Username", "email@email.com")
        def userEntity = new UserEntity(null, "Username", "email@email.com")
        def storedUser = new UserEntity(constUuid, "Username", "email@email.com")

        and: "the mapper should be called"
        1 * usersMapper.mapToEntity(userToBeSaved) >> userEntity

        and: "the repository should be invoked to save the user"
        1 * usersRepository.save(userEntity) >> storedUser

        when: "the create user method is called"
        def userId = usersService.createUser("email@email.com", "Username")

        then: "the created user should be the expected one"
        assert storedUser.id == userId
    }

    def "should throw an exception when the user is not created"() {
        given: "a user to be created"
        def userEntityToBeSaved = new UserEntity(null, "Username", "")
        def userEntity = Mock(UserEntity)
        userEntity.id >> null

        and: "the mapper should be called"
        1 * usersMapper.mapToEntity(_) >> userEntityToBeSaved

        and: "the repository should be invoked to save the user"
        1 * usersRepository.save(userEntityToBeSaved) >> userEntity

        when: "the create user method is called"
        usersService.createUser("", "Username")

        then: "an exception is thrown"
        thrown(UserNotSavedException)
    }

    def "should return a specific user"() {
        given: "an expected user to be returned"
        def expectedUserEntity = new UserEntity(constUuid, "username", "email@email.com")
        def expectedUserModel = new UserModel(constUuid, "username", "email@email.com")

        and: "the repository is invoked"
        1 * usersRepository.findById(constUuid) >> { Optional.of(expectedUserEntity) }

        and: "the mapper is invoked"
        1 * usersMapper.mapToModel(expectedUserEntity) >> expectedUserModel

        when: "the method is called to return the user"
        def result = usersService.getUserById(constUuid)


        then: "the returned user is the correct one"
        assert expectedUserEntity.id == result.id
        assert expectedUserEntity.email == result.email
        assert expectedUserEntity.username == result.username
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
        def expectedUserEntities = [new UserEntity(constUuid, "username", "email@email.com")]
        def expectedUserModelList = [new UserModel(constUuid, "username", "email@email.com")]

        and: "the repository is invoked"
        1 * usersRepository.findAll() >> { expectedUserEntities }

        and: "the mapper is invoked"
        1 * usersMapper.mapToModelList(expectedUserEntities) >> expectedUserModelList

        when: "the method is called to return the user"
        def result = usersService.findAllUsers()


        then: "the returned user is the correct one"
        assert result.size() == 1
    }

    def "should return an empty list of users"() {
        given: "an expected empty list"
        def emptyList = []

        and: "the repository is invoked"
        1 * usersRepository.findAll() >> { emptyList }

        and: "the mapper is invoked"
        0 * usersMapper.mapToModelList(_)

        when: "the method is called to return the user"
        def result = usersService.findAllUsers()


        then: "the returned user is the correct one"
        assert result.size() == 0
    }

}
