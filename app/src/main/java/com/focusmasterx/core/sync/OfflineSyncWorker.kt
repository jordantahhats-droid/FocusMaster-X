package com.focusmasterx.core.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Constraints
import androidx.work.WorkerParameters
import com.focusmasterx.core.data.FocusMasterDatabase
import com.focusmasterx.core.firebase.FirebaseSyncRepository

class OfflineSyncWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result = runCatching {
        val dao = FocusMasterDatabase.get(applicationContext).focusDao()
        val repository = FirebaseSyncRepository()
        dao.unsyncedSessions().forEach { repository.uploadSession(it) }
    }.fold(onSuccess = { Result.success() }, onFailure = { Result.retry() })

    companion object {
        fun request() = OneTimeWorkRequestBuilder<OfflineSyncWorker>()
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .build()
    }
}
