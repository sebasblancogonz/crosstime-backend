package com.crosstime.backend.mapper

import com.crosstime.backend.model.Schedule as ScheduleModel
import com.crosstime.backend.entity.Schedule as ScheduleEntity
import org.mapstruct.Mapper

@Mapper
interface ScheduleMapper {

    fun mapEntityToModel(scheduleEntity: ScheduleEntity)

    fun mapEntitiesToModels(scheduleEntities: List<ScheduleEntity>): List<ScheduleModel>

}