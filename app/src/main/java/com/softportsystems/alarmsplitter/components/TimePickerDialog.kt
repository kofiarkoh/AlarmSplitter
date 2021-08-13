package com.softportsystems.alarmsplitter.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.softportsystems.alarmsplitter.AlarmViewModel


@RequiresApi(Build.VERSION_CODES.N)
@ExperimentalAnimationApi
@Composable
fun NewAlarmDialog(alarmViewModel: AlarmViewModel){
    AnimatedVisibility(visible = alarmViewModel.isDialogVisible ) {
        Dialog(onDismissRequest = { /*TODO*/ }) {
            Card() {
                Column(modifier = Modifier.padding(20.dp) ,
                horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Start) {

                        TimeInput(value = alarmViewModel.hrs,onchange = alarmViewModel::setHours)
                        Text(
                            text = ":",
                            color = Color.Black ,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 30.sp
                        )
                        TimeInput(value = alarmViewModel.mins,onchange = alarmViewModel::setMinutes)
                        AmPmColumn(alarmViewModel.daytime,  alarmViewModel::setDaylight )
                    }

                    Row() {
                        OutlinedButton(onClick ={
                            alarmViewModel.isDialogVisible = false

                        },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent,contentColor = Color.Red),
                            modifier = Modifier.padding(20.dp)) {
                            Text(text = "Cancel")
                        }
                        Button(onClick ={
                            alarmViewModel.createAlarm()
                        },
                            modifier = Modifier.padding(20.dp)) {
                            Text(text = "SAVE")
                        }
                    }

                }
            }

        }  
    }
   
}




@Composable
fun TimeInput(value:String,onchange:(String)->Unit){
    TextField ( modifier = Modifier.width(80.dp) , value = value,onValueChange = onchange,
        textStyle = TextStyle(fontSize = 40.sp,fontWeight = FontWeight.SemiBold),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.LightGray,


            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,cursorColor = Color.Black ))
}

@Composable
fun AmPmColumn(value:String,setValue:(String)->Unit){
    Column  {
        AmPmBtn(title = "AM",bg= { if (value == "AM") Color.LightGray else Color.Transparent },
        onclick = {setValue("AM")}
            )
        AmPmBtn(title = "PM",bg= { if (value == "PM") Color.LightGray else Color.Transparent },
            onclick = {setValue("PM")})
    }
}

@Composable
fun AmPmBtn(title:String, bg: () -> Color,onclick:()->Unit){
    OutlinedButton(onClick = onclick,modifier = Modifier.padding(5.dp) ,
        border = BorderStroke(0.dp,Color.LightGray)
        ,
    colors = ButtonDefaults.outlinedButtonColors (backgroundColor = bg(),contentColor = Color.Black)
        ) {
        Text(text = title)
    }
}