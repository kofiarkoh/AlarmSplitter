package com.softportsystems.alarmsplitter.logic

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.softportsystems.alarmsplitter.AlarmReciever
import com.softportsystems.alarmsplitter.database.AppDataBase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

open class AlarmMaker(context: Context) {
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    private val context = context


    init {
        Log.d("alarmclass","alarm class initiated")
        alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    @RequiresApi(Build.VERSION_CODES.N)
     fun setAlarm(timeHr:Int, timeMins:Int, alarmId:Int){
        Log.d("setalarm","setting alarm $timeHr:$timeMins")
        alarmIntent = Intent(context, AlarmReciever::class.java).let { intent ->
            intent.putExtra("alarmId",alarmId.toString())
            PendingIntent.getBroadcast(context, alarmId, intent, 0)

        }
        // alarmIntent.

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()

            set(Calendar.HOUR_OF_DAY, timeHr)
            set(Calendar.MINUTE,timeMins)
        }


        alarmMgr?.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,
            1000 * 60 * 1, //alarm must not repeat
            alarmIntent)
    }
}