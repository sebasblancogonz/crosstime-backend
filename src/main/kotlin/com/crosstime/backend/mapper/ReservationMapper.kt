package com.crosstime.backend.mapper

import com.crosstime.backend.model.Reservation as ReservationModel
import com.crosstime.backend.entity.Reservation as ReservationEntity
import org.mapstruct.Mapper

@Mapper
interface ReservationMapper {

    fun mapEntityToModel(reservation: ReservationEntity): ReservationModel

    fun mapEntitiesToModels(reservations: List<ReservationEntity>): List<ReservationModel>

}