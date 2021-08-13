package com.softportsystems.alarmsplitter.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Alarm (

     @PrimaryKey(autoGenerate = true)  val id:Int,

    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "time") val time: String?,
    //@ColumnInfo(name = "interval") val time: String?,
    @ColumnInfo(name = "numOfTimes") val numOfTimes: String?

){
    //@Ignore
    //constructor( _id:Long?, title: String?,time: String?,numOfTimes: String?,): (null,title ,time,numOfTimes)
}

