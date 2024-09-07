package com.crosstime.backend.mapper

import com.crosstime.backend.model.AuthenticatedUser
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import com.crosstime.backend.entity.User as UserEntity
import com.crosstime.backend.model.User as UserModel

@Mapper
interface UsersMapper {

    fun mapToModel(userEntity: UserEntity): UserModel

    @Mapping(target = "alias", source = "username")
    @Mapping(target = "pwd", source = "password")
    fun mapToEntity(userModel: AuthenticatedUser): UserEntity

    fun mapToModelList(userEntities: List<UserEntity>): List<UserModel>

    fun mapToEntityList(userModels: List<UserModel>): List<UserEntity>

}