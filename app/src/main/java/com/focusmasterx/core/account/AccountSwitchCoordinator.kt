package com.focusmasterx.core.account

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AccountSwitchCoordinator(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    suspend fun requestIndividualMode(studentId: String, adminId: String) {
        val request = mapOf(
            "studentId" to studentId,
            "adminId" to adminId,
            "status" to "pending",
            "createdAt" to System.currentTimeMillis(),
            "attempts" to 0,
        )
        firestore.collection("accountSwitchRequests").add(request).await()
    }

    suspend fun reclaimChildMode(studentId: String, adminId: String) {
        firestore.collection("users").document(studentId).update(
            mapOf(
                "accountType" to "CHILD",
                "linkedAdminId" to adminId,
                "reclaimedAt" to System.currentTimeMillis(),
            ),
        ).await()
    }
}
