package com.focusmasterx.core.ui

import android.content.Intent
import android.net.VpnService
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.focusmasterx.core.focus.FocusSessionService
import com.focusmasterx.core.permissions.PermissionStatus
import com.focusmasterx.core.permissions.PermissionStatusReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val permissions = PermissionStatusReader(this).read()
        setContent {
            FocusHome(
                permissions = permissions,
                onStartFocus = {
                    VpnService.prepare(this)?.let { startActivity(it) }
                    startForegroundService(Intent(this, FocusSessionService::class.java))
                },
            )
        }
    }
}

@Composable
fun FocusHome(permissions: PermissionStatus, onStartFocus: () -> Unit) {
    Surface(Modifier.fillMaxSize().background(Color(0xFF0A0E1A)), color = Color(0xFF0A0E1A)) {
        Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Focus Master X", color = Color(0xFFE8EDF8), style = MaterialTheme.typography.headlineLarge)
            Text(
                "Android MVP: safe permission checks, foreground engine, local Room cache, and Firebase sync foundation.",
                color = Color(0xFF8896B3),
            )
            PermissionLine("VPN prepared", permissions.vpnPrepared)
            PermissionLine("Accessibility enabled", permissions.accessibilityEnabled)
            PermissionLine("Device Admin active", permissions.deviceAdminActive)
            PermissionLine("Usage Stats allowed", permissions.usageStatsAllowed)
            PermissionLine("Overlay allowed", permissions.overlayAllowed)
            Button(onClick = onStartFocus, enabled = permissions.usageStatsAllowed) { Text("Start Focus Engine") }
        }
    }
}

@Composable
private fun PermissionLine(label: String, enabled: Boolean) {
    val status = if (enabled) "Ready" else "Needs setup"
    val color = if (enabled) Color(0xFF10B981) else Color(0xFFFB7185)
    Text("$label: $status", color = color)
}
