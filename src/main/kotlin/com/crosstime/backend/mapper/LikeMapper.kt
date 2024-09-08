package com.crosstime.backend.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import com.crosstime.backend.entity.Like as LikeEntity
import com.crosstime.backend.model.Like as LikeModel

@Mapper(uses = [UsersMapper::class])
interface LikeMapper {

    @Mapping(target = "user", source = "user")
    fun mapToModel(likeEntity: LikeEntity): LikeModel

    @Mapping(target = "user", source = "user")
    fun mapToEntity(likeModel: LikeModel): LikeEntity

    fun mapToModelList(likeEntities: List<LikeEntity>): List<LikeModel>

    fun mapToEntityList(likeModels: List<LikeModel>): List<LikeEntity>

}