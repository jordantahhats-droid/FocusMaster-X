package com.focusmasterx.core.permissions

import android.net.VpnService
import android.os.ParcelFileDescriptor
import kotlinx.coroutines.*

class FocusVpnService : VpnService() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var tun: ParcelFileDescriptor? = null
    override fun onStartCommand(intent: android.content.Intent?, flags: Int, startId: Int): Int {
        tun = Builder().setSession("Focus Master X Local DNS Shield").addAddress("10.8.0.2", 32).addDnsServer("10.8.0.1").addRoute("0.0.0.0", 0).establish()
        scope.launch { runDnsShieldLoop() }
        return START_STICKY
    }
    private suspend fun runDnsShieldLoop() { while (isActive) delay(1_000) /* TODO: parse DNS packets and return NXDOMAIN for cached blocked domains. */ }
    override fun onDestroy() { scope.cancel(); tun?.close(); super.onDestroy() }
}
