package com.jonghyeok.gonow

import com.jonghyeok.gonow.domain.model.TrainArrival
import java.time.LocalTime

fun calculateDepartureTime(
    target: TrainArrival,
    walkingMinutes: Int,
    bufferMinutes: Int,
): LocalTime {
    val requiredTime = walkingMinutes + bufferMinutes
    return target.arrivalTime.minusMinutes(requiredTime.toLong())
}