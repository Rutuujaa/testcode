package com.example.testapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testapplication.ui.theme.TestapplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestapplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ClockWalkCalculator(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ClockWalkCalculator(modifier: Modifier = Modifier) {
    var timeA by remember { mutableStateOf("") }
    var timeB by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = timeA,
            onValueChange = { timeA = it },
            label = { Text("Time taken by A to cover 5 cmins (in minutes)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = timeB,
            onValueChange = { timeB = it },
            label = { Text("Time taken by B to cover 5 cmins (in minutes)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val x = timeA.toDoubleOrNull()
                val y = timeB.toDoubleOrNull()
                if (x != null && y != null && x > 0 && y > 0) {
                    val distanceA = calculateMeetingPoint(x, y)
                    result = String.format("They will meet at %.2f clock-minutes from 12-0-clock.", distanceA)
                } else {
                    result = "Please enter valid numbers for both times."
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Calculate Meeting Point")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = result, style = MaterialTheme.typography.bodyLarge)
    }
}

fun calculateMeetingPoint(timeA: Double, timeB: Double): Double {
    val speedA = 5.0 / timeA
    val speedB = 5.0 / timeB
    val totalDistance = 60.0
    val relativeSpeed = speedA + speedB
    val timeToMeet = totalDistance / relativeSpeed
    val distanceA = speedA * timeToMeet

    // Logging the inputs and the calculated result
    Log.d("ClockWalk", "Time A: $timeA, Time B: $timeB")
    Log.d("ClockWalk", "Speed A: $speedA, Speed B: $speedB")
    Log.d("ClockWalk", "Relative Speed: $relativeSpeed")
    Log.d("ClockWalk", "Time to Meet: $timeToMeet")
    Log.d("ClockWalk", "Distance A: $distanceA")

    return distanceA
}

@Preview(showBackground = true)
@Composable
fun ClockWalkCalculatorPreview() {
    TestapplicationTheme {
        ClockWalkCalculator()
    }
}