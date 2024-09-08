package com.crosstime.backend.mapper

import org.mapstruct.Mapper
import com.crosstime.backend.entity.Slot as SlotEntity
import com.crosstime.backend.model.Slot as SlotModel

@Mapper
interface SlotMapper {

    fun mapEntityToModel(entity: SlotEntity): SlotModel

    fun mapModelToEntity(model: SlotModel): SlotEntity

    fun mapEntitiesToModels(entities: List<SlotEntity>): List<SlotModel>

    fun mapModelsToEntities(models: List<SlotModel>): List<SlotEntity>
}