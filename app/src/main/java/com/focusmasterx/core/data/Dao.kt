package com.focusmasterx.core.data

import androidx.room.*

@Dao interface FocusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun upsertSession(session: FocusSessionEntity)
    @Query("SELECT * FROM focus_sessions WHERE synced = 0") suspend fun unsyncedSessions(): List<FocusSessionEntity>
    @Query("SELECT * FROM blocklist_entries") suspend fun blocklist(): List<BlocklistEntryEntity>
    @Query("SELECT * FROM blocklist_entries WHERE packageName = :packageName LIMIT 1") suspend fun blockForPackage(packageName: String): BlocklistEntryEntity?
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun upsertBlocklist(entries: List<BlocklistEntryEntity>)
    @Query("SELECT * FROM timetable_entries WHERE day = :day AND confirmed = 1") suspend fun timetableForDay(day: String): List<TimetableEntryEntity>
}
