package com.focusmasterx.core.timetable

import com.focusmasterx.core.data.TimetableEntryEntity
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class TimetableSchedulerTest {
    private val scheduler = TimetableScheduler()

    @Test
    fun findsActiveEntryForCurrentSchoolPeriod() {
        val entry = entry("Biology", "Monday", "09:00", "10:00")
        val active = scheduler.activeEntry(LocalDateTime.of(2026, 6, 22, 9, 30), listOf(entry))
        assertEquals("Biology", active?.subject)
    }

    private fun entry(subject: String, day: String, start: String, end: String) = TimetableEntryEntity(
        entryId = subject,
        userId = "student-1",
        subject = subject,
        day = day,
        startTime = start,
        endTime = end,
        confirmed = true,
    )
}
