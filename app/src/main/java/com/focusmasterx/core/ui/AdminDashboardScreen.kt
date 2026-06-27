package com.focusmasterx.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.focusmasterx.core.data.UserProfile

@Composable
fun AdminDashboard(students: List<UserProfile>, onReclaimControl: (UserProfile) -> Unit, onGenerateOtp: (UserProfile) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Admin Control Center", color = Color(0xFFE8EDF8))
        students.forEach { student ->
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(student.displayName, color = Color(0xFFE8EDF8))
                    Text("${student.streakCount} day streak • ${student.totalFocusMinutes / 60}h total focus", color = Color(0xFF8896B3))
                    Button(onClick = { onGenerateOtp(student) }) { Text("Approve and Send PIN") }
                    Button(onClick = { onReclaimControl(student) }) { Text("Reclaim Control") }
                }
            }
        }
    }
}
