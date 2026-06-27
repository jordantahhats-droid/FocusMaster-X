package com.focusmasterx.core.timetable

import com.focusmasterx.core.data.TimetableEntryEntity
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TimetableScheduler {
    private val formatter = DateTimeFormatter.ofPattern("HH:mm")

    fun activeEntry(now: LocalDateTime, entries: List<TimetableEntryEntity>): TimetableEntryEntity? {
        val day = now.dayOfWeek.toFocusDayName()
        val time = now.toLocalTime()
        return entries.firstOrNull { entry ->
            entry.day.equals(day, ignoreCase = true) &&
                !time.isBefore(LocalTime.parse(entry.startTime, formatter)) &&
                time.isBefore(LocalTime.parse(entry.endTime, formatter))
        }
    }

    fun nextEntry(now: LocalDateTime, entries: List<TimetableEntryEntity>): TimetableEntryEntity? {
        val day = now.dayOfWeek.toFocusDayName()
        val time = now.toLocalTime()
        return entries
            .filter { it.day.equals(day, ignoreCase = true) && LocalTime.parse(it.startTime, formatter).isAfter(time) }
            .minByOrNull { LocalTime.parse(it.startTime, formatter) }
    }

    private fun DayOfWeek.toFocusDayName(): String = name.lowercase().replaceFirstChar { it.titlecase() }
}
