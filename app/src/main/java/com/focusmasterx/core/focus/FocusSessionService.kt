package com.focusmasterx.core.focus

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.focusmasterx.core.data.FocusMasterDatabase
import com.focusmasterx.core.permissions.UsageStatsForegroundAppDetector
import com.focusmasterx.core.ui.StudyTimeOverlayActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class FocusSessionService : Service() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val decisionEngine = BlockDecisionEngine()
    private lateinit var detector: UsageStatsForegroundAppDetector
    private val dao by lazy { FocusMasterDatabase.get(this).focusDao() }
    private var lastOverlayPackage: String? = null
    private var lastOverlayAt: Long = 0L

    override fun onCreate() {
        super.onCreate()
        detector = UsageStatsForegroundAppDetector(this)
        startForeground(NOTIFICATION_ID, notification())
        scope.launch { enforcementLoop() }
    }

    private suspend fun enforcementLoop() {
        while (isActive) {
            val packageName = detector.currentForegroundPackage()
            val blockEntry = packageName?.let { dao.blockForPackage(it) }
            val decision = decisionEngine.decide(packageName, blockEntry)
            if (decision.shouldBlock) {
                launchOverlayThrottled(decision)
            }
            delay(POLL_INTERVAL_MS)
        }
    }

    private fun launchOverlayThrottled(decision: BlockDecision) {
        val now = System.currentTimeMillis()
        if (lastOverlayPackage == decision.packageName && now - lastOverlayAt < OVERLAY_THROTTLE_MS) return
        lastOverlayPackage = decision.packageName
        lastOverlayAt = now
        startActivity(
            Intent(this, StudyTimeOverlayActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                .putExtra(StudyTimeOverlayActivity.EXTRA_PACKAGE_NAME, decision.packageName)
                .putExtra(StudyTimeOverlayActivity.EXTRA_BLOCK_REASON, decision.reason),
        )
    }

    private fun notification(): Notification {
        val channel = NotificationChannel(CHANNEL_ID, "Active focus sessions", NotificationManager.IMPORTANCE_LOW)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Focus Master X is active")
            .setContentText("Monitoring distractions and enforcing your current session.")
            .setSmallIcon(android.R.drawable.ic_lock_lock)
            .setOngoing(true)
            .build()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    companion object {
        private const val CHANNEL_ID = "focus_session"
        private const val NOTIFICATION_ID = 7
        private const val POLL_INTERVAL_MS = 500L
        private const val OVERLAY_THROTTLE_MS = 2_500L
    }
}
