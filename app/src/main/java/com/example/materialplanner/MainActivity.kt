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
                        name= "PLACEHOLDER",
                        start= 1658024800000,
                        end = 1658028400000
                    ),
                    Event(
                        name= "PLACEHOLDER2",
                        start= 1658024800000,
                        end = 1658028400000
                    ),
                    Event(
                        name= "PLACEHOLDER3",
                        start= 1658024800000,
                        end = 1658028400000
                    ),
                )
                schedule(dataList = dataList)
            }
        }
    }
}

val chipHeight=98.dp
val chipScale=16.dp
var elevation = 0.dp
var shadow=0.dp

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaterialPlannerTheme {
        Greeting("Android")
    }
}

data class Event(
    val name: String="",
    val start: Long,
    val end: Long
)

@Composable
fun timeSlot(
    data: Event,
) {
    /*Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(chipScale),
        color =MaterialTheme.colorScheme.surface,
        tonalElevation =6.dp,
    ) {
        Text(text = data.name,
            modifier=Modifier.padding(all = 8.dp)
            )

    }*/
    Surface(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(chipScale),
        color = MaterialTheme.colorScheme.primary,
        tonalElevation = 6.dp,
    ) {
        var time1= formatTime(time = data.start)
        var time2= formatTime(time = data.end)
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
                "Remaining: hh:mm:ss",
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

//        Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier.padding(8.dp)
//        ) {
//            Text(
//                text = data.name,
//                textAlign = TextAlign.Left,
//                style = MaterialTheme.typography.headlineSmall,
//            )
//            Column(
//                //horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier
//                    .padding(8.dp)
//
//            ) {
//
//                Text(
//                    "Time Remaining: hh:mm:ss",
//                    textAlign = TextAlign.Right,
//                    style = MaterialTheme.typography.bodyMedium,
//                )
//                Text(
//                    "START $time1\nEND $time2",
//                    textAlign = TextAlign.Right,
//                    //modifier=Modifier.align(alignment = Alignment.Bottom),
//                    style = MaterialTheme.typography.bodyMedium,
//                )
//            }
//        }
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
        items(dataList) { data ->
            timeSlot(data = data)
        }
    }
}
@Composable
fun formatTime(time: Long): String {
    val dateTime = Date(time)
    val timeFormatter = SimpleDateFormat("hh:mm a")
    val formattedTime = timeFormatter.format(dateTime)

    return formattedTime
}
