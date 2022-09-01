package com.example.stunting.data.db.model.reminder

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "data_reminder")
data class DataReminder(
    @PrimaryKey
    val id_reminder: String,
    @ColumnInfo(name = "clock")
    val clock: String,
    @ColumnInfo(name = "note")
    val note: String,
    @ColumnInfo(name = "monday")
    val monday: Boolean,
    @ColumnInfo(name = "tuesday")
    val tuesday: Boolean,
    @ColumnInfo(name = "wednesday")
    val wednesday: Boolean,
    @ColumnInfo(name = "thursday")
    val thursday: Boolean,
    @ColumnInfo(name = "friday")
    val friday: Boolean,
    @ColumnInfo(name = "saturday")
    val saturday: Boolean,
    @ColumnInfo(name = "sunday")
    val sunday: Boolean,
    @ColumnInfo(name = "status")
    var status: Boolean
) : Parcelable