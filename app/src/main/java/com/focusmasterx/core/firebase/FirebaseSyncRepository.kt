package com.focusmasterx.core.firebase

import com.focusmasterx.core.data.FocusSessionEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseSyncRepository(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    suspend fun uploadSession(session: FocusSessionEntity) { firestore.collection("users").document(session.userId).collection("sessions").document(session.sessionId).set(session).await() }
    fun listenForAccountType(userId: String, onChanged: (String) -> Unit) = firestore.collection("users").document(userId).addSnapshotListener { snapshot, _ -> snapshot?.getString("accountType")?.let(onChanged) }
}
