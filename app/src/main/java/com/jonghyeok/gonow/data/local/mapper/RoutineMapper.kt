package com.jonghyeok.gonow.data.local.mapper

import com.jonghyeok.gonow.data.local.entity.RoutineEntity
import com.jonghyeok.gonow.domain.model.Routine

fun Routine.toEntity(): RoutineEntity {
    return RoutineEntity(
        id = id,
        name = name,
        days = days.joinToString(",") { it.name },
        startTimeMinutes = startTime.hour * 60 + startTime.minute,
        endTimeMinutes = endTime.hour * 60 + endTime.minute,
        stationId = subwayRoute.station.id,
        stationName = subwayRoute.station.name,
        lineId = subwayRoute.line.id,
        lineName = subwayRoute.line.name,
        directionId = subwayRoute.direction.id,
        directionName = subwayRoute.direction.name,
        walkingMinutes = walkingMinutes,
        bufferMinutes = bufferMinutes,
    )
}