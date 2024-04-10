package com.crosstime.backend.mapper

import com.crosstime.backend.model.User as UserModel
import com.crosstime.backend.entity.User as UserEntity
import org.mapstruct.Mapper

@Mapper
interface UsersMapper {

    fun mapToModel(userEntity: UserEntity): UserModel

    fun mapToEntity(userModel: UserModel): UserEntity

}