package com.jonghyeok.gonow.data.local.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.jonghyeok.gonow.data.local.entity.RoutineEntity

@Dao
interface RoutineDao {

    @Insert
    suspend fun insert(routine: RoutineEntity)

    @Update
    suspend fun update(routine: RoutineEntity)

    @Delete
    suspend fun delete(routine: RoutineEntity)

    @Query("SELECT * FROM routines")
    suspend fun getAll(): List<RoutineEntity>
}