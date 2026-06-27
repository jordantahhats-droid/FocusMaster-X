package com.focusmasterx.core.permissions

import android.app.AppOpsManager
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.net.VpnService
import android.os.Build
import android.provider.Settings
import android.view.accessibility.AccessibilityManager

data class PermissionStatus(
    val vpnPrepared: Boolean,
    val accessibilityEnabled: Boolean,
    val deviceAdminActive: Boolean,
    val usageStatsAllowed: Boolean,
    val overlayAllowed: Boolean,
)

class PermissionStatusReader(private val context: Context) {
    fun read(): PermissionStatus = PermissionStatus(
        vpnPrepared = VpnService.prepare(context) == null,
        accessibilityEnabled = isAccessibilityEnabled(),
        deviceAdminActive = context.getSystemService(DevicePolicyManager::class.java).isAdminActive(ComponentName(context, FocusDeviceAdminReceiver::class.java)),
        usageStatsAllowed = hasUsageStatsAccess(),
        overlayAllowed = Settings.canDrawOverlays(context),
    )

    private fun isAccessibilityEnabled(): Boolean {
        val manager = context.getSystemService(AccessibilityManager::class.java)
        return manager.getEnabledAccessibilityServiceList(android.accessibilityservice.AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
            .any { it.resolveInfo.serviceInfo.packageName == context.packageName && it.resolveInfo.serviceInfo.name == FocusAccessibilityService::class.java.name }
    }

    private fun hasUsageStatsAccess(): Boolean {
        val appOps = context.getSystemService(AppOpsManager::class.java)
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOps.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.packageName)
        } else {
            @Suppress("DEPRECATION") appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.packageName)
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }
}
