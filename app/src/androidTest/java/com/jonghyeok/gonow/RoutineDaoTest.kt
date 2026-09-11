package com.jonghyeok.gonow

import android.content.Context
import androidx.room3.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jonghyeok.gonow.data.local.dao.RoutineDao
import com.jonghyeok.gonow.data.local.database.GoNowDatabase
import com.jonghyeok.gonow.data.local.entity.RoutineEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoutineDaoTest {

    private lateinit var database: GoNowDatabase
    private lateinit var routineDao: RoutineDao

    @Before
    fun setUp() {
        val context =
            ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            GoNowDatabase::class.java,
        ).build()

        routineDao = database.routineDao()
    }

    @Test
    fun `루틴을 저장하면 다시 조회할 수 있다`() = runBlocking {
        // Given
        val routine = RoutineEntity(
            id = "routine-1",
            name = "출근",
            days = "MONDAY,TUESDAY,WEDNESDAY",
            startTimeMinutes = 470,
            endTimeMinutes = 520,
            stationId = "station-1",
            stationName = "강남역",
            lineId = "line-2",
            lineName = "2호선",
            directionId = "direction-1",
            directionName = "잠실·성수 방면",
            walkingMinutes = 8,
            bufferMinutes = 2,
        )

        // When
        routineDao.insert(routine)

        val routines = routineDao.getAll()

        // Then
        Assert.assertEquals(1, routines.size)
        Assert.assertEquals(routine, routines.first())
    }

    @After
    fun tearDown() {
        if (::database.isInitialized) {
            database.close()
        }
    }

    @Test
    fun `루틴을 수정하면 변경된 값으로 조회된다`() = runBlocking {
        // Given
        val original = RoutineEntity(
            id = "routine-1",
            name = "출근",
            days = "MONDAY,TUESDAY,WEDNESDAY",
            startTimeMinutes = 470,
            endTimeMinutes = 520,
            stationId = "station-1",
            stationName = "강남역",
            lineId = "line-2",
            lineName = "2호선",
            directionId = "direction-1",
            directionName = "잠실·성수 방면",
            walkingMinutes = 8,
            bufferMinutes = 2,
        )

        routineDao.insert(original)

        val updated = original.copy(
            name = "출근 루틴",
            walkingMinutes = 10,
        )

        // When
        routineDao.update(updated)

        val routines = routineDao.getAll()

        // Then
        Assert.assertEquals(1, routines.size)
        Assert.assertEquals("출근 루틴", routines.first().name)
        Assert.assertEquals(10, routines.first().walkingMinutes)
    }

    @Test
    fun `루틴을 삭제하면 조회되지 않는다`() = runBlocking {
        // Given
        val routine = RoutineEntity(
            id = "routine-1",
            name = "출근",
            days = "MONDAY,TUESDAY,WEDNESDAY",
            startTimeMinutes = 470,
            endTimeMinutes = 520,
            stationId = "station-1",
            stationName = "강남역",
            lineId = "line-2",
            lineName = "2호선",
            directionId = "direction-1",
            directionName = "잠실·성수 방면",
            walkingMinutes = 8,
            bufferMinutes = 2,
        )

        routineDao.insert(routine)

        // When
        routineDao.delete(routine)

        val routines = routineDao.getAll()

        // Then
        Assert.assertTrue(routines.isEmpty())
    }

    @Test
    fun `저장된 모든 루틴을 조회할 수 있다`() = runBlocking {
        // Given
        val routine1 = RoutineEntity(
            id = "routine-1",
            name = "출근",
            days = "MONDAY,TUESDAY,WEDNESDAY",
            startTimeMinutes = 470,
            endTimeMinutes = 520,
            stationId = "station-1",
            stationName = "강남역",
            lineId = "line-2",
            lineName = "2호선",
            directionId = "direction-1",
            directionName = "잠실·성수 방면",
            walkingMinutes = 8,
            bufferMinutes = 2,
        )

        val routine2 = RoutineEntity(
            id = "routine-2",
            name = "퇴근",
            days = "MONDAY,TUESDAY,WEDNESDAY",
            startTimeMinutes = 1080,
            endTimeMinutes = 1140,
            stationId = "station-2",
            stationName = "역삼역",
            lineId = "line-2",
            lineName = "2호선",
            directionId = "direction-2",
            directionName = "강남·교대 방면",
            walkingMinutes = 5,
            bufferMinutes = 3,
        )

        routineDao.insert(routine1)
        routineDao.insert(routine2)

        // When
        val routines = routineDao.getAll()

        // Then
        Assert.assertEquals(2, routines.size)
        Assert.assertTrue(routines.contains(routine1))
        Assert.assertTrue(routines.contains(routine2))
    }
}