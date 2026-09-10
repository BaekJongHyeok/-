package com.jonghyeok.gonow.data.local.mapper

import com.google.android.libraries.places.api.model.DayOfWeek
import com.jonghyeok.gonow.data.local.entity.RoutineEntity
import com.jonghyeok.gonow.domain.model.Routine
import kotlin.collections.joinToString

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

fun RoutineEntity.toDomain(): Routine {
    return Routine(
        id = id,
        name = name,
        days = days
            .split(",")
            .map { DayOfWeek.valueOf(it) }
            .toSet(),

        startTime = LocalTime.of(startTimeMinutes / 60, startTimeMinutes % 60,),
        endTime = LocalTime.of(endTimeMinutes / 60, endTimeMinutes % 60,),

        subwayRoute = SubwayRoute(
            station = Station(id = stationId, name = stationName,),
            line = SubwayLine(id = lineId, name = lineName,),
            direction = Direction(id = directionId, name = directionName,),
        ),

        walkingMinutes = walkingMinutes,
        bufferMinutes = bufferMinutes,
    )
}