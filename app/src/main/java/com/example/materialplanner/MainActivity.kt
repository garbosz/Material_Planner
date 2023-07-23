package com.example.materialplanner

import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.materialplanner.ui.theme.MaterialPlannerTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialPlannerTheme {
                // A surface container using the 'background' color from the theme
                val dataList = listOf(
                    Event(
                        name= "TITLE",
                        start= 1633012000,
                        end = 1633078400
                    ),
                    Event(
                        name= "TITLE2",
                        start= 1633078400,
                        end = 1633144800
                    ),
                    Event(
                        name= "TITLE3",
                        start= 1633211200,
                        end = 1633277600
                    ),
                )
                schedule(dataList = dataList)
            }
        }
    }
}

@Preview
@Composable
fun Preview(){
    //super.onCreate(savedInstanceState)

            // A surface container using the 'background' color from the theme
    val dataList = listOf(
        Event(
            name= "TITLE",
            start= 1690059600000,
            end = 1690063200000
        ),
        Event(
            name= "TITLE2",
            start= 1690063200000,
            end = 1690066800000
        ),
        Event(
            name= "TITLE3",
            start= 1690070400000,
            end = 1690074000000
        ),
    )
    schedule(dataList = dataList)

    }

val chipHeight=98.dp
val chipScale=16.dp
var elevation = 0.dp
var shadow=0.dp

data class Event(
    val name: String="",
    val start: Long,
    val end: Long
)

@Composable
fun timeSlot(
    data: Event,
) {
    Surface(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(chipScale),
        color = MaterialTheme.colorScheme.primary,
        tonalElevation = 6.dp,
    ) {
        //val scope = CoroutineScope(Dispatchers.IO)
        //var timer= MutableStateFlow(String)
        var time1= formatTime(time = data.start)
        var time2= formatTime(time = data.end)
        var now = System.currentTimeMillis()
        var target:Long
        if (now < data.start) {
            target = data.start
        } else {
            target = data.end
        }
        var timer= countdownTimer(target)
        // Update the timer every second
//        scope.launch {
//            while (true) {
//                delay(1000)
//                timer = countdownTimer(target)
//            }
//        }
        Text(
            text = data.name,
            style= MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(8.dp)
        )
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                "Remaining: $timer",
                //textAlign = TextAlign.Right,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(vertical = 2.dp)
            )
            Text(
                "START $time1\nEND $time2",
                //textAlign = TextAlign.Right,
                //modifier=Modifier.align(alignment = Alignment.Bottom),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun schedule(
    dataList: List<Event>
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var now=
        items(dataList) { data ->
            timeSlot(data = data)
        }
    }
}

@Composable
fun countdownTimer(target: Long, now: Long = System.currentTimeMillis()): MutableStateFlow<String.Companion> {
    val remainingTime = target - now
    val seconds = remainingTime / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val roundedMinutes = minutes.toLong()
    val roundedSeconds = seconds % 60.toLong()
    val formattedSeconds = if (roundedSeconds < 10) {
        "0$roundedSeconds"
    } else {
        "$roundedSeconds"
    }
    return when {
        hours > 0 -> "$hours:$roundedMinutes:$roundedSeconds"
        minutes > 0 -> "00:$roundedMinutes:$roundedSeconds"
        else -> "00:00:$roundedSeconds"
    }
}


@Composable
fun formatTime(time: Long): String {
    val dateTime = Date(time)
    val timeFormatter = SimpleDateFormat("hh:mm a")
    val formattedTime = timeFormatter.format(dateTime)

    return formattedTime
}

