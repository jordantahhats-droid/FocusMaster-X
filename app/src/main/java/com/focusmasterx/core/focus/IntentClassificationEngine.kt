package com.focusmasterx.core.focus

import java.util.Locale

data class IntentClassificationResult(
    val allowed: Boolean,
    val score: Double,
    val matchedKeywords: List<String>,
)

class IntentClassificationEngine {
    fun classify(screenText: String, subject: String, subjectKeywords: List<String>): IntentClassificationResult {
        val normalizedText = screenText.lowercase(Locale.US)
        val normalizedSubject = subject.lowercase(Locale.US)
        val keywords = (subjectKeywords + normalizedSubject.split(" ")).map { it.lowercase(Locale.US).trim() }.filter { it.length >= 3 }.distinct()
        val matches = keywords.filter { normalizedText.contains(it) }
        val score = if (keywords.isEmpty()) 0.0 else matches.size.toDouble() / keywords.size.toDouble()
        return IntentClassificationResult(allowed = score >= ACADEMIC_THRESHOLD, score = score, matchedKeywords = matches)
    }

    companion object {
        private const val ACADEMIC_THRESHOLD = 0.25
    }
}
