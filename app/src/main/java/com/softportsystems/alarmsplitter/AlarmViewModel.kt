package com.softportsystems.alarmsplitter

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.softportsystems.alarmsplitter.database.Alarm
import com.softportsystems.alarmsplitter.database.AppDataBase
import com.softportsystems.alarmsplitter.logic.AlarmMaker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class AlarmViewModel(application: Application): AndroidViewModel(application) {
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    var isDialogVisible :Boolean by mutableStateOf(false)

    var hrs:String by mutableStateOf("12")
    var mins:String by mutableStateOf("00")
    var daytime:String by mutableStateOf("AM")
    var alarms:List<Alarm> by mutableStateOf(listOf())
    lateinit var db:AppDataBase
    val context = application.applicationContext
    private lateinit var alarmMaker: AlarmMaker



    init {

        viewModelScope.launch {
            db =Room.databaseBuilder(context,AppDataBase::class.java,
                "alarm-db").build()
            alarmMaker = AlarmMaker(context = context)

         //   alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        }
    }

    fun toggleDialog(){
      isDialogVisible  = !isDialogVisible
    }

    fun setMinutes(_mins:String){
        Log.d("hrschange",_mins)
        if (_mins.toInt() <10){
            mins = "0${_mins.toInt()}"
        }
        else if (_mins.toInt() > 60){
            mins = "00"
        }else{
            mins = _mins.toInt().toString()
        }

    }
    fun setHours(_hrs:String) {
        if (_hrs.isEmpty()){
            hrs = "12"
        }
            else if(_hrs.toInt() > 12){
                hrs = _hrs.last().toString() //get last character
        }
        //remove leading zeros
        else{
            hrs = _hrs.toInt().toString()
        }

    }
    fun setDaylight(value:String){
        daytime = value
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun createAlarm(){
        //hide dialog
        isDialogVisible = false
        var alarmtime = "$hrs:$mins $daytime"
        //Log.d("setalarm","hours is $hrs $daytime")
        var alarmHour = if (daytime === "AM") hrs.toInt() else hrs.toInt()+12
        //Log.d("setalarm","alarm hour is $alarmHour ")

        try {
            viewModelScope.launch(Dispatchers.IO) {
                val randomValues = List(10) { Random.nextInt(0, 9999) }
                val alarmId = randomValues[0]

                var _alarm = Alarm(alarmId,"Alarm",alarmtime,"9")
                db.alarmDao().insertAll(_alarm)

                //refresh alarms
                getAlarms()

                //send alarm intent
               var start_mins =  mins.toInt()
              //  for (i in 1..1){

                   alarmMaker. setAlarm(alarmHour,start_mins,alarmId)
                    start_mins += 1
               // }


            }

        }
       catch (e:Exception){
           Log.d("createalarm",e.message.toString())
       }
    }

    fun getAlarms(){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                 alarms =  db.alarmDao().getAll()
                Log.d("createalarms",alarms.toString())

            }

        }
        catch (e:Exception){
            Log.d("createalarm",e.message.toString())
        }
    }

    fun deleteAlarm(alarm: Alarm){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                db.alarmDao().delete(alarm)
                Log.d("createalarm","alarm deleted")

                //cancel alarm from system
                cancelAlarm(alarmId = alarm.id)
                //refresh alarms
                getAlarms()

            }

        }
        catch (e:Exception){
            Log.d("createalarm",e.message.toString())
        }
    }


   private fun cancelAlarm(alarmId: Int){
        alarmIntent = Intent(context, AlarmReciever::class.java).let { intent ->
           // intent.putExtra("alarmId",alarmId.toString())
            PendingIntent.getBroadcast(context, alarmId, intent, 0)

        }
        alarmMgr?.cancel(alarmIntent)
    }
}