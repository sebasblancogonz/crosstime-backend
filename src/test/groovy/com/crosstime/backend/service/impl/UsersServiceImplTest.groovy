package com.crosstime.backend.service.impl

import com.crosstime.backend.mapper.UsersMapper
import com.crosstime.backend.repository.UsersRepository
import com.crosstime.backend.model.User as UserModel
import com.crosstime.backend.entity.User as UserEntity
import org.mockito.MockedStatic
import spock.lang.Specification

import static org.mockito.Mockito.mockStatic

class UsersServiceImplTest extends Specification {

    private static MockedStatic<UUID> mockUuid
    private static final UUID constUuid = UUID.randomUUID()
    private UsersMapper usersMapper = Mock(UsersMapper.class)
    private UsersRepository usersRepository = Mock(UsersRepository.class)
    private UsersServiceImpl usersService

    def setup() {
        mockUuid = mockStatic(UUID.class)
        mockUuid.when(UUID::randomUUID).thenReturn(constUuid)
        usersService = new UsersServiceImpl(usersRepository, usersMapper)
    }

    def "should store successfully the user and return the uuid"() {
        given: "a user to be created"
        def userToBeSaved = new UserModel(constUuid, "Username", "email@email.com")
        def userEntity = new UserEntity(constUuid, "Username", "email@email.com")

        when: "the create user method is called"
        def userId = usersService.createUser("email@email.com", "Username")

        then: "the mapper should be called"
        1 * usersMapper.mapToEntity(userToBeSaved) >> userEntity

        then: "the repository should be invoked to save the user"
        1 * usersRepository.save(userEntity) >> userEntity

        then: "the created user should be the expected one"
        assert userToBeSaved.id == userId
    }

}
