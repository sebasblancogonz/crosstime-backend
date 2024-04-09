package com.crosstime.backend.mapper

import spock.lang.Specification

class UsersMapperTest extends Specification {

    private UsersMapper usersMapper

    def setup() {
        usersMapper = new UsersMapperImpl()
    }

    def "should map a user entity to user model"() {
        given: "a user entity"
        def userEntity = new UserEntity(UUID.randomUUID(), "Username", "email@email.com")

        when: "the entity to model mapper is invoked"
        def mappedModel = usersMapper.mapToModel(userEntity)

        then: "it should be correctly mapped"
        assert userEntity.email == mappedModel.email
        assert userEntity.id == mappedModel.id
        assert userEntity.username == mappedModel.username
    }

    def "should map a user model to a user entity"() {
        given: "a user model"
        def userModel = new UserModel(UUID.randomUUID(), "Username", "email@email.com")

        when: "the entity to model mapper is invoked"
        def mappedEntity = usersMapper.mapToEntity(userModel)

        then: "it should be correctly mapped"
        assert userModel.email == mappedEntity.email
        assert userModel.id == mappedEntity.id
        assert userModel.username == mappedEntity.username
    }

}
