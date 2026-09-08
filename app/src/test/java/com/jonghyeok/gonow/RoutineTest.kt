package com.jonghyeok.gonow

import com.jonghyeok.gonow.domain.model.Direction
import com.jonghyeok.gonow.domain.model.Routine
import com.jonghyeok.gonow.domain.model.Station
import com.jonghyeok.gonow.domain.model.SubwayLine
import com.jonghyeok.gonow.domain.model.SubwayRoute
import com.jonghyeok.gonow.domain.model.TrainArrival
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalTime

class RoutineTest {

    private val defaultSubwayRoute = SubwayRoute(
        station = Station("station-1", "강남역"),
        line = SubwayLine("line-2", "2호선"),
        direction = Direction("direction-1", "잠실·성수 방면"),
    )

    val defaultArrivals = listOf(
        TrainArrival("A", LocalTime.of(8, 7)),
        TrainArrival("B", LocalTime.of(8, 10)),
        TrainArrival("C", LocalTime.of(8, 15)),
    )

    /**
     * 루틴 활성화 테스트
     */
    @Test
    fun `화요일 오전 8시 10분은 활성 시간이다`() {
        val routine = createRoutine()

        val result = routine.isActive(
            dayOfWeek = DayOfWeek.TUESDAY,
            currentTime = LocalTime.of(8, 10),
        )

        assertTrue(result)
    }

    @Test
    fun `일요일 오전 8시 10분은 비활성 상태이다`() {
        val routine = createRoutine()

        val result = routine.isActive(
            dayOfWeek = DayOfWeek.SUNDAY,
            currentTime = LocalTime.of(8, 10),
        )

        assertFalse(result)
    }

    @Test
    fun `화요일 오전 7시 49분은 비활성 상태이다`() {
        val routine = createRoutine()

        val result = routine.isActive(
            dayOfWeek = DayOfWeek.TUESDAY,
            currentTime = LocalTime.of(7, 49),
        )

        assertFalse(result)
    }

    @Test
    fun `화요일 오전 7시 50분은 활성 상태이다`() {
        val routine = createRoutine()

        val result = routine.isActive(
            dayOfWeek = DayOfWeek.TUESDAY,
            currentTime = LocalTime.of(7, 50),
        )

        assertTrue(result)
    }

    @Test
    fun `화요일 오전 8시 40분은 활성 상태이다`() {
        val routine = createRoutine()

        val result = routine.isActive(
            dayOfWeek = DayOfWeek.TUESDAY,
            currentTime = LocalTime.of(8, 40),
        )

        assertTrue(result)
    }

    @Test
    fun `화요일 오전 8시 41분은 비활성 상태이다`() {
        val routine = createRoutine()

        val result = routine.isActive(
            dayOfWeek = DayOfWeek.TUESDAY,
            currentTime = LocalTime.of(8, 41),
        )

        assertFalse(result)
    }

    private fun createRoutine(
        name: String = "출근",
        days: Set<DayOfWeek> = setOf(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
        ),
        startTime: LocalTime = LocalTime.of(7, 50),
        endTime: LocalTime = LocalTime.of(8, 40),
        walkingMinutes: Int = 8,
        bufferMinutes: Int = 3,
    ): Routine {
        return Routine(
            id = "1",
            name = name,
            days = days,
            startTime = startTime,
            endTime = endTime,
            subwayRoute = defaultSubwayRoute,
            walkingMinutes = walkingMinutes,
            bufferMinutes = bufferMinutes,
        )
    }


    /**
     * 루틴 세부 내용 테스트
     */
    @Test
    fun `루틴 이름이 비어있으면 생성할 수 없다`() {
        assertThrows(IllegalArgumentException::class.java) {
            createRoutine(
                name = "",
            )
        }
    }

    @Test
    fun `루틴 이름이 공백만 있다면 생성할 수 없다`() {
        assertThrows(IllegalArgumentException::class.java) {
            createRoutine(
                name = " "
            )
        }
    }

    @Test
    fun `요일이 선택되지 않았다면 생성할 수 없다`() {
        assertThrows(IllegalArgumentException::class.java) {
            createRoutine(
                days = emptySet()
            )
        }
    }

    @Test
    fun `루틴 시작 시간이 루틴 종료 시간보다 이후라면 생성할 수 없다`() {
        assertThrows(IllegalArgumentException::class.java) {
            createRoutine(
                startTime = LocalTime.of(8, 40),
                endTime = LocalTime.of(7, 50)

            )
        }
    }

    @Test
    fun `루틴 시작 시간이 루틴 종료 시간과 동일하다면 생성할 수 없다`() {
        assertThrows(IllegalArgumentException::class.java) {
            createRoutine(
                startTime = LocalTime.of(8, 0),
                endTime = LocalTime.of(8,0)
            )
        }
    }

    @Test
    fun `역까지 이동시간이 0이면 생성할 수 없다`() {
        assertThrows(IllegalArgumentException::class.java) {
            createRoutine(
                walkingMinutes = 0
            )
        }
    }

    @Test
    fun `역까지 이동시간이 1분이면 생성할 수 있다`() {
        createRoutine(
            walkingMinutes = 1
        )
    }

    @Test
    fun `역까지 이동시간이 음수이면 생성할 수 없다`() {
        assertThrows(IllegalArgumentException::class.java) {
            createRoutine(
                walkingMinutes = -1
            )
        }
    }

    @Test
    fun `여유시간이 2분 미만이면 생성할 수 없다`() {
        assertThrows(IllegalArgumentException::class.java) {
            createRoutine(
                bufferMinutes = 1
            )
        }
    }

    @Test
    fun `여유시간이 2분이면 생성할 수 있다`() {
        createRoutine(
            bufferMinutes = 2
        )
    }

    @Test
    fun `여유시간이 음수이면 생성할 수 없다`() {
        assertThrows(IllegalArgumentException::class.java) {
            createRoutine(
                bufferMinutes = -1
            )
        }
    }


    /**
     * 도착 지하철 선택 알고리즘
     */
    @Test
    fun `이동시간과 여유시간을 만족하는 가장 빠른 열차를 선택한다`() {
        val arrivals = listOf(
            TrainArrival(
                id = "A",
                arrivalTime = LocalTime.of(8, 7),
            ),
            TrainArrival(
                id = "B",
                arrivalTime = LocalTime.of(8, 9),
            ),
            TrainArrival(
                id = "C",
                arrivalTime = LocalTime.of(8, 10),
            ),
            TrainArrival(
                id = "D",
                arrivalTime = LocalTime.of(8, 14),
            )
        )

        val result = selectTrain(
            currentTime = LocalTime.of(8, 0),
            walkingMinutes = 8,
            bufferMinutes = 2,
            arrivals = arrivals,
        )

        assertEquals("C", result?.id)
    }

    @Test
    fun `이동시간과 여유시간을 만족하는 가장 빠른 열차가 없다`() {
        val arrivals = listOf(
            TrainArrival(
                id = "A",
                arrivalTime = LocalTime.of(8, 3),
            ),
            TrainArrival(
                id = "B",
                arrivalTime = LocalTime.of(8, 5),
            ),
            TrainArrival(
                id = "C",
                arrivalTime = LocalTime.of(8, 9),
            )
        )

        val result = selectTrain(
            currentTime = LocalTime.of(8, 0),
            walkingMinutes = 8,
            bufferMinutes = 2,
            arrivals = arrivals,
        )

        assertNull(result)
    }

    @Test
    fun `정렬되지 않은 열차 목록에서도 가장 빠른 탑승 가능 열차를 선택한다`() {
        val arrivals = listOf(
            TrainArrival(
                id = "D",
                arrivalTime = LocalTime.of(8, 14),
            ),
            TrainArrival(
                id = "C",
                arrivalTime = LocalTime.of(8, 10),
            ),
            TrainArrival(
                id = "A",
                arrivalTime = LocalTime.of(8, 7),
            ),
            TrainArrival(
                id = "B",
                arrivalTime = LocalTime.of(8, 9),
            )
        )

        val result = selectTrain(
            currentTime = LocalTime.of(8, 0),
            walkingMinutes = 8,
            bufferMinutes = 2,
            arrivals = arrivals,
        )

        assertEquals("C", result?.id)
    }

    @Test
    fun `정확히 최소 탑승 가능 시각에 도착하는 열차는 선택한다`() {
        val arrivals = listOf(
            TrainArrival(
                id = "C",
                arrivalTime = LocalTime.of(8, 10),
            )
        )

        val result = selectTrain(
            currentTime = LocalTime.of(8, 0),
            walkingMinutes = 8,
            bufferMinutes = 2,
            arrivals = arrivals,
        )

        assertEquals("C", result?.id)
    }

    @Test
    fun `최소 탑승 가능 시각보다 1분 빠른 열차는 선택하지 않는다`() {
        val arrivals = listOf(
            TrainArrival(
                id = "B",
                arrivalTime = LocalTime.of(8, 9),
            )
        )

        val result = selectTrain(
            currentTime = LocalTime.of(8, 0),
            walkingMinutes = 8,
            bufferMinutes = 2,
            arrivals = arrivals,
        )

        assertNull(result)
    }

    @Test
    fun `열차 목록이 비어 있으면 null을 반환한다`() {
        val arrivals = emptyList<TrainArrival>()

        val result = selectTrain(
            currentTime = LocalTime.of(8, 0),
            walkingMinutes = 8,
            bufferMinutes = 2,
            arrivals = arrivals,
        )

        assertNull(result)
    }

    @Test
    fun `역 도착 예상 시간이 열차 도착 시간보다 빠르면 타겟을 유지한다`() {
        val result = canKeepTarget(
            currentTime = LocalTime.of(8, 1),
            walkingMinutes = 8,
            target = TrainArrival("A", LocalTime.of(8, 10)),
        )

        assertTrue(result)
    }

    @Test
    fun `정확히 도착시간과 같다면 유지한다`() {
        val result = canKeepTarget(
            currentTime = LocalTime.of(8, 2),
            walkingMinutes = 8,
            target = TrainArrival("A", LocalTime.of(8, 10)),
        )

        assertTrue(result)
    }

    @Test
    fun `역 도착 예상 시간이 열차 도착 시간보다 늦으면 타겟을 유지할 수 없다`() {
        val result = canKeepTarget(
            currentTime = LocalTime.of(8, 3),
            walkingMinutes = 8,
            target = TrainArrival("A", LocalTime.of(8, 10)),
        )

        assertFalse(result)
    }

    @Test
    fun `기존 타겟이 없으면 새 타겟을 선택한다`() {
        val result = resolveTargetTrain(
            currentTime = LocalTime.of(8, 0),
            walkingMinutes = 8,
            bufferMinutes = 2,
            currentTarget = null,
            arrivals = defaultArrivals,
        )

        assertEquals("B", result?.id)
    }

    @Test
    fun `기존 타겟이 있고, 최신 도착정보로 봐도 여전히 탈 수 있으면 그 타겟을 유지한다`() {
        val result = resolveTargetTrain(
            currentTime = LocalTime.of(8,1),
            walkingMinutes = 8,
            bufferMinutes = 2,
            currentTarget = TrainArrival("A", LocalTime.of(8, 15)),
            arrivals = listOf(
                TrainArrival("A", LocalTime.of(8, 10)),
                TrainArrival("B", LocalTime.of(8, 14))
            )
        )

        assertEquals("A", result?.id)
        assertEquals(LocalTime.of(8,10), result?.arrivalTime)
    }

    @Test
    fun `기존 A가 최신 정보 기준으로는 더이상 탈수 없으면 새 타겟을 찾는다`() {
        val result = resolveTargetTrain(
            currentTime = LocalTime.of(8, 3),
            walkingMinutes = 8,
            bufferMinutes = 2,
            currentTarget = TrainArrival("A", LocalTime.of(8, 15)),
            arrivals = listOf(
                TrainArrival("A", LocalTime.of(8, 10)),
                TrainArrival("B", LocalTime.of(8, 14))
            )
        )

        assertEquals("B", result?.id)
        assertEquals(LocalTime.of(8, 14), result?.arrivalTime)
    }


}