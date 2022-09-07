package com.stuntech.stunting.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stuntech.stunting.data.db.dao.ReminderDao
import com.stuntech.stunting.data.db.model.reminder.DataReminder

@Database(
    entities = [
        DataReminder::class
    ],
    version = 1,
    exportSchema = true

)
abstract class RoomDB : RoomDatabase() {

    abstract fun reminderDao(): ReminderDao

    companion object {
        @Volatile
        private var instance: RoomDB? = null

        fun getInstance(context: Context): RoomDB {
            return instance ?: synchronized(this) {
                val instances = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    "stuntung.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                instance = instances
                instances
            }
        }

    }

}