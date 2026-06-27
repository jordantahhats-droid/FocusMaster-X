package com.focusmasterx.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.focusmasterx.core.data.LeaderboardEntry
import com.focusmasterx.core.data.UserProfile
import com.focusmasterx.core.data.WeeklyReport

@Composable
fun StudentDashboard(profile: UserProfile, report: WeeklyReport?, leaderboard: List<LeaderboardEntry>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        StatRow(
            items = listOf(
                "Streak" to "${profile.streakCount} days",
                "Best" to "${profile.bestStreak} days",
                "Focus" to "${profile.totalFocusMinutes / 60}h ${profile.totalFocusMinutes % 60}m",
            ),
        )
        report?.let {
            FocusCard(title = it.headline, body = it.body)
        }
        leaderboard.take(3).forEach { entry ->
            FocusCard(title = "#${entry.rank} ${entry.displayName}", body = "${entry.weeklyFocusMinutes / 60}h this week • ${entry.currentStreak} day streak")
        }
    }
}

@Composable
private fun StatRow(items: List<Pair<String, String>>) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items.forEach { (label, value) ->
            Card(Modifier.weight(1f)) {
                Column(Modifier.padding(12.dp)) {
                    Text(label, color = Color(0xFF8896B3))
                    Text(value, color = Color(0xFFE8EDF8))
                }
            }
        }
    }
}

@Composable
private fun FocusCard(title: String, body: String) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(title, color = Color(0xFFE8EDF8))
            Text(body, color = Color(0xFF8896B3))
        }
    }
}
