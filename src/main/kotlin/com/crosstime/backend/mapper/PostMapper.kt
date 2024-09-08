package com.crosstime.backend.mapper

import org.mapstruct.Mapper
import com.crosstime.backend.entity.Post as PostEntity
import com.crosstime.backend.model.Post as PostModel

@Mapper(uses = [UsersMapper::class, LikeMapper::class])
interface PostMapper {

    fun mapToModel(postEntity: PostEntity): PostModel

    fun mapToEntity(postModel: PostModel): PostEntity

    fun mapToModelList(postEntities: List<PostEntity>): List<PostModel>

    fun mapToEntityList(postModels: List<PostModel>): List<PostEntity>

}