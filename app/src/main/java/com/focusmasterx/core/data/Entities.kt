package com.focusmasterx.core.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class AccountType { INDIVIDUAL, CHILD, ADMIN }
enum class LockIntensity { SOFT, MEDIUM, STRICT }
enum class BlockType { ALWAYS, DUAL_USE, WHITELISTED }

@Entity(tableName = "focus_sessions")
data class FocusSessionEntity(@PrimaryKey val sessionId: String, val userId: String, val startTime: Long, val endTime: Long?, val durationMinutes: Int, val subject: String, val bypassAttempts: Int = 0, val appealsSubmitted: Int = 0, val appealsApproved: Int = 0, val synced: Boolean = false)

@Entity(tableName = "timetable_entries")
data class TimetableEntryEntity(@PrimaryKey val entryId: String, val userId: String, val subject: String, val day: String, val startTime: String, val endTime: String, val room: String = "", val confirmed: Boolean = false)

@Entity(tableName = "blocklist_entries")
data class BlocklistEntryEntity(@PrimaryKey val entryId: String, val userId: String, val packageName: String, val domainPatterns: List<String>, val blockType: BlockType, val appliedBy: String)

@Entity(tableName = "streaks")
data class StreakEntity(@PrimaryKey val userId: String, val streakCount: Int, val bestStreak: Int, val updatedAt: Long)
