package com.jonghyeok.gonow.domain.repository

import com.jonghyeok.gonow.domain.model.Routine

interface RoutineRepository {

    suspend fun insert(routine: Routine)

    suspend fun update(routine: Routine)

    suspend fun delete(routine: Routine)

    suspend fun getAll(): List<Routine>
}