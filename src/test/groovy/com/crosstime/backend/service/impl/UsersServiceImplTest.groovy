package com.crosstime.backend.service.impl

import com.crosstime.backend.mapper.UsersMapper
import com.crosstime.backend.repository.UsersRepository
import com.crosstime.backend.model.User as UserModel
import com.crosstime.backend.entity.User as UserEntity
import org.mockito.MockedStatic
import spock.lang.Specification

import static org.mockito.Mockito.mockStatic

class UsersServiceImplTest extends Specification {

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

        when: "the create user method is called"
        def userId = usersService.createUser("email@email.com", "Username")

        then: "the mapper should be called"
        1 * usersMapper.mapToEntity(userToBeSaved) >> userEntity

        then: "the repository should be invoked to save the user"
        1 * usersRepository.save(userEntity) >> storedUser

        then: "the created user should be the expected one"
        assert storedUser.id == userId
    }

    def "should return a specific user"() {
        given: "the userId: " + constUuid
        and: "an expected user to be returned"
        def expectedUserEntity = new UserEntity(constUuid, "username", "email@email.com")

        and: "an expected user model"
        def expectedUserModel = new UserModel(constUuid, "username", "email@email.com")

        when: "the method is called to return the user"
        def result = usersService.getUserById(constUuid)

        and: "the repository is invoked"
        1 * usersRepository.findById(constUuid) >> expectedUserEntity

        and: "the mapper is invoked"
        1 * usersMapper.mapToModel(expectedUserModel) >> expectedUserModel

        then: "the returned user is the correct one"
        assert result.id == expectedUserEntity.id
        assert result.email == expectedUserEntity.email
        assert result.username == expectedUserEntity.username
    }

}
