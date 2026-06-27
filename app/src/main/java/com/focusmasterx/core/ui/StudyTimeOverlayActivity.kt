package com.focusmasterx.core.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class StudyTimeOverlayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val blocked = intent.getStringExtra(EXTRA_PACKAGE_NAME).orEmpty()
        val reason = intent.getStringExtra(EXTRA_BLOCK_REASON).orEmpty()
        setContent {
            Surface(Modifier.fillMaxSize(), color = Color(0xFF0A0E1A)) {
                Column(Modifier.padding(28.dp), verticalArrangement = Arrangement.Center) {
                    Text("Study Time", color = Color(0xFFE8EDF8), style = MaterialTheme.typography.displaySmall)
                    Text("$blocked is blocked during this focus session.", color = Color(0xFF8896B3))
                    if (reason.isNotBlank()) Text(reason, color = Color(0xFF00D4FF))
                    Spacer(Modifier.height(24.dp))
                    Text("Return to your current subject to keep your streak alive.", color = Color(0xFF10B981))
                }
            }
        }
    }

    companion object {
        const val EXTRA_PACKAGE_NAME = "blocked_package_name"
        const val EXTRA_BLOCK_REASON = "block_reason"
    }
}
