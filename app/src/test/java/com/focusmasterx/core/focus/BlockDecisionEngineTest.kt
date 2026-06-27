package com.focusmasterx.core.focus

import com.focusmasterx.core.data.BlockType
import com.focusmasterx.core.data.BlocklistEntryEntity
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BlockDecisionEngineTest {
    private val engine = BlockDecisionEngine()

    @Test
    fun unlistedPackageIsAllowed() {
        val decision = engine.decide("com.example.notes", null)
        assertFalse(decision.shouldBlock)
    }

    @Test
    fun alwaysBlockedPackageIsBlocked() {
        val decision = engine.decide("com.social.app", entry(BlockType.ALWAYS))
        assertTrue(decision.shouldBlock)
    }

    @Test
    fun whitelistedPackageIsAllowed() {
        val decision = engine.decide("com.browser", entry(BlockType.WHITELISTED))
        assertFalse(decision.shouldBlock)
    }

    private fun entry(type: BlockType) = BlocklistEntryEntity(
        entryId = "entry-1",
        userId = "student-1",
        packageName = "com.social.app",
        domainPatterns = listOf("*.example.com"),
        blockType = type,
        appliedBy = "admin-1",
    )
}
