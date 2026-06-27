package com.focusmasterx.core.permissions

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class FocusDeviceAdminReceiver : DeviceAdminReceiver() {
    override fun onEnabled(context: Context, intent: Intent) = Toast.makeText(context, "Focus Master X protection enabled", Toast.LENGTH_SHORT).show()
    override fun onDisableRequested(context: Context, intent: Intent): CharSequence = "Child accounts require admin approval before disabling protection."
    override fun onDisabled(context: Context, intent: Intent) = Toast.makeText(context, "Focus Master X protection disabled", Toast.LENGTH_SHORT).show()
}
