package com.crosstime.backend.mapper

import org.mapstruct.Mapper
import com.crosstime.backend.entity.User as UserEntity
import com.crosstime.backend.model.User as UserModel

@Mapper
interface UsersMapper {

    fun mapToModel(userEntity: UserEntity): UserModel
    fun mapToEntity(userModel: UserModel): UserEntity
    fun mapToModelList(userEntities: List<UserEntity>): List<UserModel>
    fun mapToEntityList(userModels: List<UserModel>): List<UserEntity>

}