package com.jonghyeok.gonow.data.repository

import com.jonghyeok.gonow.data.local.dao.RoutineDao
import com.jonghyeok.gonow.data.local.mapper.toDomain
import com.jonghyeok.gonow.data.local.mapper.toEntity
import com.jonghyeok.gonow.domain.model.Routine
import com.jonghyeok.gonow.domain.repository.RoutineRepository

class RoutineRepositoryImpl(
    private val routineDao: RoutineDao,
) : RoutineRepository {

    override suspend fun insert(routine: Routine) {
        routineDao.insert(routine.toEntity())
    }

    override suspend fun update(routine: Routine) {
        routineDao.update(routine.toEntity())
    }

    override suspend fun delete(routine: Routine) {
        routineDao.delete(routine.toEntity())
    }

    override suspend fun getAll(): List<Routine> {
        return routineDao.getAll()
            .map { it.toDomain() }
    }
}