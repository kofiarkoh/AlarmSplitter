package com.softportsystems.alarmsplitter.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface  AlarmDAO{
    @Query("SELECT * FROM alarm")
    fun getAll(): List<Alarm>

    @Insert
    fun insertAll(vararg users: Alarm)

    @Delete
    fun delete(user: Alarm)


}