package com.crosstime.backend.mapper

import org.mapstruct.factory.Mappers
import spock.lang.Specification

import com.crosstime.backend.model.User as UserModel
import com.crosstime.backend.entity.User as UserEntity

class UsersMapperSpec extends Specification {

    private UsersMapper usersMapper

    def setup() {
        usersMapper = Mappers.getMapper(UsersMapper.class)
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

    def "should map a user entity list to user model list"() {
        given: "a user entity"
        def userEntities = [new UserEntity(UUID.randomUUID(), "Username", "email@email.com")]

        when: "the entity to model mapper is invoked"
        def mappedModels = usersMapper.mapToModelList(userEntities)

        then: "it should be correctly mapped"
        assert mappedModels.size() == 1
        assert userEntities[0].email == mappedModels[0].email
        assert userEntities[0].id == mappedModels[0].id
        assert userEntities[0].username == mappedModels[0].username
    }

    def "should map a user model list to a user entity list"() {
        given: "a user model"
        def userModels = [new UserModel(UUID.randomUUID(), "Username", "email@email.com")]

        when: "the entity to model mapper is invoked"
        def mappedEntities = usersMapper.mapToEntityList(userModels)

        then: "it should be correctly mapped"
        assert mappedEntities.size() == 1
        assert userModels[0].email == mappedEntities[0].email
        assert userModels[0].id == mappedEntities[0].id
        assert userModels[0].username == mappedEntities[0].username
    }

}
