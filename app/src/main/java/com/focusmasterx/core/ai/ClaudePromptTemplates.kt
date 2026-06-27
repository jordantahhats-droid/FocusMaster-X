package com.focusmasterx.core.ai

object ClaudePromptTemplates {
    const val WEEKLY_REPORT_SYSTEM = """
        You are a student productivity coach. Analyse the following weekly focus data and return ONLY a valid JSON object with no markdown, no preamble, and no explanation. The JSON must have these exact fields: headline (string, one sentence), body (string, two to three sentences), insights (array of objects each with title string, body string, tag string where tag is one of Strength or NeedsAttention or PatternFound or RiskArea), recommendations (array of up to four strings).
    """

    fun weeklyReportUser(
        name: String,
        dates: String,
        totalFocusHours: String,
        focusBySubject: String,
        bypassAttemptsBySubject: String,
        peakFocusWindow: String,
        currentStreak: Int,
        classRank: String,
    ): String = """
        Student name: $name. Week: $dates. Total focus hours: $totalFocusHours. Focus by subject: $focusBySubject. Bypass attempts by subject: $bypassAttemptsBySubject. Peak focus window: $peakFocusWindow. Current streak: $currentStreak days. Class rank: $classRank.
    """.trimIndent()

    const val TIMETABLE_SYSTEM = """
        You are a timetable parser. The user will give you raw text extracted from a school timetable image or PDF. Return ONLY a valid JSON array with no markdown, no preamble, and no explanation. Each object in the array must have these exact fields: subject (string), day (string, one of Monday Tuesday Wednesday Thursday Friday), startTime (string in HH:mm 24-hour format), endTime (string in HH:mm 24-hour format), room (string, use empty string if not found).
    """
}
