package com.focusmasterx.core.data

data class UserProfile(
    val userId: String = "",
    val email: String = "",
    val displayName: String = "",
    val accountType: AccountType = AccountType.INDIVIDUAL,
    val linkedAdminId: String = "",
    val deviceToken: String = "",
    val streakCount: Int = 0,
    val bestStreak: Int = 0,
    val totalFocusMinutes: Int = 0,
    val settings: UserSettings = UserSettings(),
)

data class UserSettings(
    val strictMode: Boolean = false,
    val weekendStreak: Boolean = false,
    val autoActivateFromTimetable: Boolean = true,
    val lockIntensity: LockIntensity = LockIntensity.MEDIUM,
    val notificationsEnabled: Boolean = true,
)

data class LeaderboardEntry(
    val userId: String = "",
    val displayName: String = "",
    val weeklyFocusMinutes: Int = 0,
    val currentStreak: Int = 0,
    val rank: Int = 0,
)

data class WeeklyReport(
    val reportId: String = "",
    val userId: String = "",
    val weekStartDate: String = "",
    val headline: String = "",
    val body: String = "",
    val insights: List<ReportInsight> = emptyList(),
    val recommendations: List<String> = emptyList(),
    val generatedAt: Long = 0L,
)

data class ReportInsight(
    val title: String = "",
    val body: String = "",
    val tag: String = "PatternFound",
)
