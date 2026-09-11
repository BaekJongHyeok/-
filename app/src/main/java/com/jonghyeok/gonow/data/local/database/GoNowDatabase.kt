package com.jonghyeok.gonow.data.local.database

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.jonghyeok.gonow.data.local.dao.RoutineDao
import com.jonghyeok.gonow.data.local.entity.RoutineEntity

@Database(
    entities = [
        RoutineEntity::class,
    ],
    version = 1,
)
abstract class GoNowDatabase : RoomDatabase() {

    abstract fun routineDao(): RoutineDao
}