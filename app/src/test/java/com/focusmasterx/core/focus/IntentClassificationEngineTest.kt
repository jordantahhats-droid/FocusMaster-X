package com.focusmasterx.core.focus

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class IntentClassificationEngineTest {
    private val engine = IntentClassificationEngine()

    @Test
    fun allowsAcademicContentThatMatchesSubjectKeywords() {
        val result = engine.classify(
            screenText = "Photosynthesis explained with chlorophyll diagrams",
            subject = "Biology",
            subjectKeywords = listOf("photosynthesis", "chlorophyll", "cells"),
        )
        assertTrue(result.allowed)
    }

    @Test
    fun blocksEntertainmentContentWhenKeywordsDoNotMatch() {
        val result = engine.classify(
            screenText = "funny gaming clips and reaction videos",
            subject = "Biology",
            subjectKeywords = listOf("photosynthesis", "chlorophyll", "cells"),
        )
        assertFalse(result.allowed)
    }
}
