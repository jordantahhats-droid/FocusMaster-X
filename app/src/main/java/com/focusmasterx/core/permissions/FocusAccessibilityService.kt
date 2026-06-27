package com.focusmasterx.core.permissions

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class FocusAccessibilityService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        // The foreground service owns block decisions. This service is intentionally conservative
        // until the intent classifier module can inspect dual-use app content safely.
    }

    override fun onInterrupt() = Unit
}
