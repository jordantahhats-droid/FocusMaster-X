package com.focusmasterx.core.permissions

import android.app.usage.UsageStatsManager
import android.content.Context

class UsageStatsForegroundAppDetector(private val context: Context) {
    fun currentForegroundPackage(now: Long = System.currentTimeMillis()): String? {
        val usage = context.getSystemService(UsageStatsManager::class.java)
        return usage.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, now - 5_000, now).maxByOrNull { it.lastTimeUsed }?.packageName
    }
}
