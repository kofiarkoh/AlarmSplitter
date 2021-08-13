package com.softportsystems.alarmsplitter.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softportsystems.alarmsplitter.R
import com.softportsystems.alarmsplitter.database.Alarm
import com.softportsystems.alarmsplitter.ui.theme.Shapes

@Composable
fun AlarmCardItem(alarm: Alarm,deleteItem:(Alarm)->Unit){
    Card( modifier = Modifier
        .fillMaxWidth()
        .height(90.dp)
        .padding(5.dp).padding(horizontal = 10.dp) ,
    content ={ AlarmCardContent(alarm = alarm,deleteItem)} )
}

@Composable
fun AlarmCardContent(alarm:Alarm,deleteItem:(Alarm)->Unit){
    Row( horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
        ) {
        IconButton(
            content = {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_alarm_24) ,
                    contentDescription ="delete alarm" ,tint = Color.Gray)
            }
            , onClick = { /*TODO*/ })
        Column() {
            Text(text = "${alarm.title.toString()} (${alarm.numOfTimes})")
            Text(text = alarm.time.toString(),fontWeight = FontWeight.ExtraBold,fontSize = 20.sp )
        }
        IconButton(
            content = {
                      Icon(painter = painterResource(id = R.drawable.ic_baseline_auto_delete_24 ) ,
                          contentDescription ="delete alarm",tint = Color.Red )
            }
            , onClick = {deleteItem(alarm) })

    }
}



@Composable
fun NewAlarmBtn(setDialog:()->Unit){
    OutlinedButton(onClick = { setDialog() }, content  = {
        Icon(painter = painterResource(id = R.drawable.ic_baseline_add_24) ,
            contentDescription = "New Alarm", tint = Color.White,
        modifier = Modifier.padding(20.dp).size(20.dp))
    },
        //modifier = Modifier.background(Color.Red),
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Red),
        shape = CircleShape
    )
}

@Composable
fun TitleText(title:String){
    Text(
        text = title,
        color = Color.White,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 30.sp,
        fontFamily = FontFamily.Monospace
    )
}