package com.softportsystems.alarmsplitter

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softportsystems.alarmsplitter.components.AlarmCardItem
import com.softportsystems.alarmsplitter.components.NewAlarmBtn
import com.softportsystems.alarmsplitter.components.NewAlarmDialog
import com.softportsystems.alarmsplitter.components.TitleText
import com.softportsystems.alarmsplitter.ui.theme.AlarmSplitterTheme
import android.media.RingtoneManager

import android.media.Ringtone

import android.net.Uri
import com.softportsystems.alarmsplitter.ui.theme.AppBG

class MainActivity : ComponentActivity() {
    private  val alarmViewModel by viewModels<AlarmViewModel>()
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

    @RequiresApi(Build.VERSION_CODES.N)
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alarmMgr = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(applicationContext, AlarmReciever::class.java).let { intent ->
            PendingIntent.getBroadcast(applicationContext, 0, intent, 0)
        }

        setContent {
            AlarmSplitterTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = AppBG,modifier = Modifier.fillMaxHeight()) {
                    Column(modifier = Modifier
                        .fillMaxHeight()
                        .padding(bottom = 20.dp),verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                        LaunchedEffect(true ){
                            alarmViewModel.getAlarms()
                        }
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 10.dp)) {
                            TitleText(title="Your Alarms")
                        }
                       LazyColumn(
                           modifier = Modifier.weight(.8f),
                       ) {
                           itemsIndexed(items=alarmViewModel.alarms,itemContent = {index,item->
                               AlarmCardItem(item,alarmViewModel::deleteAlarm)
                           })
                       }
                        NewAlarmBtn(setDialog = alarmViewModel::toggleDialog)

                    }
                    NewAlarmDialog(alarmViewModel = alarmViewModel )


                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun setSystemAlarm(hrs:Int,mins:Int){
        Log.d("setalarm","setting alarm")

       val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hrs)
            set(Calendar.MINUTE,mins)
        }

        alarmMgr?.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,alarmIntent)
    }


}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AlarmSplitterTheme {
        Greeting("Android")
    }
}