package com.focusmasterx.core.data

import android.content.Context
import androidx.room.*

@Database(entities = [FocusSessionEntity::class, TimetableEntryEntity::class, BlocklistEntryEntity::class, StreakEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class FocusMasterDatabase : RoomDatabase() {
    abstract fun focusDao(): FocusDao
    companion object {
        @Volatile private var instance: FocusMasterDatabase? = null
        fun get(context: Context): FocusMasterDatabase = instance ?: synchronized(this) { instance ?: Room.databaseBuilder(context, FocusMasterDatabase::class.java, "focus_master_x.db").build().also { instance = it } }
    }
}
