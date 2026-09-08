package com.jonghyeok.gonow.domain.model

import java.time.LocalTime

data class TrainArrival(
    val id: String,
    val arrivalTime: LocalTime,
)