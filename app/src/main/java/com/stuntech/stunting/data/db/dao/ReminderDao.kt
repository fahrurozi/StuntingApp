package com.stuntech.stunting.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.stuntech.stunting.data.db.model.reminder.DataReminder

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: List<DataReminder>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: DataReminder)

    @Query("SELECT * FROM data_reminder ORDER BY id_reminder DESC")
    fun getData(): List<DataReminder>

    @Query("SELECT * FROM data_reminder WHERE id_reminder = :id")
    fun getDataID(id: String): DataReminder

    @Query("SELECT * FROM data_reminder ORDER BY id_reminder DESC")
    fun getDataLive(): LiveData<List<DataReminder>>

    @Delete
    fun deleteData(data: DataReminder)

    @Query("DELETE FROM data_reminder")
    fun deleteAllData()

    @Query("DELETE FROM data_reminder")
    fun updateQty()

    @Update
    fun update(data: DataReminder)
}