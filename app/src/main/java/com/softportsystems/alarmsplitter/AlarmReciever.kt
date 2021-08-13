package com.softportsystems.alarmsplitter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class AlarmReciever : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
       Log.d("alarmbroa","broadcast recieved for alarmid ${intent?.getStringExtra("alarmId")}")
        playAlarmTone(context)

    }

    private  fun deleteAlarm(){

    }

    private fun playAlarmTone(context: Context?){
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val r = RingtoneManager.getRingtone(context, notification)
        r.play()
    }
}