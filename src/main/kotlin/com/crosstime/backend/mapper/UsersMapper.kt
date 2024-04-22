package com.crosstime.backend.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import com.crosstime.backend.entity.User as UserEntity
import com.crosstime.backend.model.User as UserModel

@Mapper
interface UsersMapper {

    @Mapping(target = "username", source = "alias")
    @Mapping(target = "password", source = "pwd")
    fun mapToModel(userEntity: UserEntity): UserModel

    @Mapping(target = "alias", source = "username")
    @Mapping(target = "pwd", source = "password")
    fun mapToEntity(userModel: UserModel): UserEntity

    fun mapToModelList(userEntities: List<UserEntity>): List<UserModel>

    fun mapToEntityList(userModels: List<UserModel>): List<UserEntity>

}