package com.focusmasterx.core.focus

import com.focusmasterx.core.data.BlockType
import com.focusmasterx.core.data.BlocklistEntryEntity

data class BlockDecision(
    val packageName: String,
    val shouldBlock: Boolean,
    val reason: String,
    val isDualUse: Boolean = false,
)

class BlockDecisionEngine {
    fun decide(packageName: String?, entry: BlocklistEntryEntity?): BlockDecision {
        if (packageName.isNullOrBlank()) {
            return BlockDecision("", shouldBlock = false, reason = "No foreground package detected")
        }
        if (entry == null) {
            return BlockDecision(packageName, shouldBlock = false, reason = "Package is not on the blocklist")
        }
        return when (entry.blockType) {
            BlockType.ALWAYS -> BlockDecision(packageName, shouldBlock = true, reason = "Package is always blocked")
            BlockType.DUAL_USE -> BlockDecision(packageName, shouldBlock = true, reason = "Dual-use package needs intent classification", isDualUse = true)
            BlockType.WHITELISTED -> BlockDecision(packageName, shouldBlock = false, reason = "Package is temporarily whitelisted")
        }
    }
}
