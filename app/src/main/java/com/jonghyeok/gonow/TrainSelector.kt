package com.jonghyeok.gonow

import com.jonghyeok.gonow.domain.model.TrainArrival
import java.time.LocalTime

fun selectTrain(
    currentTime: LocalTime,
    walkingMinutes: Int,
    bufferMinutes: Int,
    arrivals: List<TrainArrival>,
): TrainArrival? {

    val requiredMinutes = walkingMinutes + bufferMinutes
    val targetTime =
        currentTime.plusMinutes(requiredMinutes.toLong())

    val sortedArrivals = arrivals.sortedBy { it.arrivalTime }

    for (arrival in sortedArrivals) {
        if (arrival.arrivalTime >= targetTime) {
            return arrival
        }
    }

    return null
}

fun canKeepTarget(
    currentTime: LocalTime,
    walkingMinutes: Int,
    target: TrainArrival
): Boolean {
    return currentTime.plusMinutes(walkingMinutes.toLong()) <= target.arrivalTime
}

fun resolveTargetTrain(
    currentTime: LocalTime,
    walkingMinutes: Int,
    bufferMinutes: Int,
    currentTarget: TrainArrival?,
    arrivals: List<TrainArrival>,
): TrainArrival? {

    // 1. currentTarget이 Null 이면 새 열차
    if (currentTarget == null) {
        return selectTrain(
            currentTime = currentTime,
            walkingMinutes = walkingMinutes,
            bufferMinutes = bufferMinutes,
            arrivals = arrivals,
        )
    }

    val latestTarget = arrivals.firstOrNull {
        it.id == currentTarget.id
    }

    // 2. currentTarget이 있으면 같은 Id의 열차로 유지 가능 여부 확인
    if (latestTarget != null && canKeepTarget(currentTime = currentTime, walkingMinutes = walkingMinutes, target = latestTarget)) {
        return latestTarget
    }


    // 3. currentTarget이 더이상 탈 수 없다면 새 target 선정
    return selectTrain(
        currentTime = currentTime,
        walkingMinutes = walkingMinutes,
        bufferMinutes = bufferMinutes,
        arrivals = arrivals,
    )
}