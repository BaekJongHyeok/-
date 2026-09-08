package com.jonghyeok.gonow.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "routines")
data class RoutineEntity(

    @PrimaryKey
    val id: String,

    val name: String,

    val days: String,

    val startTimeMinutes: Int,
    val endTimeMinutes: Int,

    val stationId: String,
    val stationName: String,

    val lineId: String,
    val lineName: String,

    val directionId: String,
    val directionName: String,

    val walkingMinutes: Int,
    val bufferMinutes: Int,
)