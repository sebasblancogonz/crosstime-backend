package com.crosstime.backend.mapper

import org.mapstruct.Mapper
import com.crosstime.backend.entity.Comment as CommentEntity
import com.crosstime.backend.model.Comment as CommentModel

@Mapper(uses = [UsersMapper::class])
interface CommentMapper {

    fun mapToModel(commentEntity: CommentEntity): CommentModel

    fun mapToEntity(commentModel: CommentModel): CommentEntity

    fun mapToModelList(commentEntities: List<CommentEntity>): List<CommentModel>

    fun mapToEntityList(commentModels: List<CommentModel>): List<CommentEntity>

}