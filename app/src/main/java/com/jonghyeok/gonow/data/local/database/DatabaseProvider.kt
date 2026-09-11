package com.jonghyeok.gonow.data.local.database

import android.content.Context
import androidx.room3.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: GoNowDatabase? = null

    fun getDatabase(context: Context): GoNowDatabase {
        return INSTANCE ?: synchronized(this) {

            val instance = Room.databaseBuilder(
                context.applicationContext,
                GoNowDatabase::class.java,
                "gonow_database"
            ).build()

            INSTANCE = instance

            instance
        }
    }
}