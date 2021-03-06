package com.softportsystems.alarmsplitter.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Alarm::class),version = 1)
abstract class AppDataBase:RoomDatabase() {
    abstract  fun alarmDao():AlarmDAO
}