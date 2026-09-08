package com.jonghyeok.gonow.domain.model

data class Station(
    val id: String,
    val name: String,
)

data class SubwayLine(
    val id: String,
    val name: String,
)

data class Direction(
    val id: String,
    val name: String,
)

data class SubwayRoute(
    val station: Station,
    val line: SubwayLine,
    val direction: Direction,
)