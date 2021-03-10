package com.example.androiddevchallenge.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.purple500


@Composable
fun Controllers(
    modifier: Modifier = Modifier,
    onPause: () -> Unit,
    onStart: () -> Unit,
    onStop: () -> Unit
) {
    val outlineButtonColor = ButtonDefaults.outlinedButtonColors(
        contentColor = purple500,
    )
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
        OutlinedButton(onClick = onStop, colors = outlineButtonColor,
            border = BorderStroke(1.dp, color = Color.Blue),
            shape = CircleShape, modifier = Modifier
                .height(48.dp)
                .width(48.dp)) {
            Icon(Icons.Default.Refresh, contentDescription = "Refresh")
        }
        OutlinedButton(onClick = onStart, shape = CircleShape, modifier = Modifier
            .height(64.dp)
            .width(64.dp)) {
            Icon(Icons.Default.PlayArrow, contentDescription = "Start")
        }
        //Button(onClick = onPause, style = OutlinedButtonS)

        OutlinedButton(onClick = onPause, colors = outlineButtonColor,
            border = BorderStroke(1.dp, color = Color.Blue),
            shape = CircleShape, modifier = Modifier.height(48.dp).width(48.dp)) {
            Icon(Icons.Default.Pause, contentDescription = "Pause")
        }
    }
}