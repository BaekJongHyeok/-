package com.jonghyeok.gonow.domain.model

import java.time.DayOfWeek
import java.time.LocalTime

data class Routine (
    val id: String,
    val name: String,

    val days: Set<DayOfWeek>,
    val startTime: LocalTime,
    val endTime: LocalTime,

    val subwayRoute: SubwayRoute,

    val walkingMinutes: Int,
    val bufferMinutes: Int,
) {
    init {
        require(name.isNotBlank()) {
            "루틴 이름은 비어 있을 수 없습니다."
        }

        require(days.isNotEmpty()) {
            "최소 하나 이상의 요일을 선택해야 합니다."
        }

        require(endTime > startTime) {
            "종료 시간은 시작 시간보다 이후여야 합니다."
        }

        require(walkingMinutes > 0) {
            "역까지 이동시간은 0분보다 커야합니다."
        }

        require(bufferMinutes >= 2) {
            "여유시간은 최소 2분 이상이어야 합니다."
        }
    }

    fun isActive(
        dayOfWeek: DayOfWeek,
        currentTime: LocalTime
    ): Boolean {
        return dayOfWeek in days && currentTime >= startTime && currentTime <= endTime
    }
}