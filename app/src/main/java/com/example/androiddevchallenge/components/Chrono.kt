package com.example.androiddevchallenge.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

@ExperimentalAnimationApi
@Composable
fun Chrono(
    modifier: Modifier = Modifier,
    seconds: Int,
    totalTimes: Float,
    enabled: Boolean,
    onMinChange: (Int) -> Unit,
    onSecChange: (Int) -> Unit,
) {
    val progressAngle by animateFloatAsState(
        targetValue = 360f / totalTimes * seconds,
        animationSpec = tween(500)
    )
    Box(
        modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        DisplayTime {

            when (enabled) {
                true ->
                    Text(
                        text = formatTime(seconds),
                        style = MaterialTheme.typography.h4,
                        color = Color(0xFFFBFBFC),
                        modifier = Modifier.align(alignment = Alignment.Center)
                    )
                false ->
                    InputView(
                        modifier = Modifier.align(alignment = Alignment.Center),
                        onMinChange = onMinChange,
                        onSecChange = onSecChange
                    )
            }

        }
        when (enabled) {
            true -> CircleProgress(angle = progressAngle)
        }

    }
}

@Composable
fun InputView(
    modifier: Modifier = Modifier,
    onMinChange: (Int) -> Unit,
    onSecChange: (Int) -> Unit
) {
    var min by remember { mutableStateOf(TextFieldValue("1")) }
    var sec by remember { mutableStateOf(TextFieldValue("0")) }
    Row(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = min,
                keyboardOptions= KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                onValueChange = {
                    min = it
                    if(it.text!="") onMinChange(it.text.toInt())
                }, textStyle = TextStyle(textAlign = TextAlign.Center, color = Color.White, fontWeight = FontWeight.Bold,  fontSize = 12.sp),
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
            )
            Text("mm", color = Color.White, modifier = Modifier.padding(top = 8.dp))
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = sec,
                keyboardOptions= KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                onValueChange = {
                    sec = it
                    if(it.text!="") onSecChange(it.text.toInt())
                }, textStyle = TextStyle(textAlign = TextAlign.Center, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp
                ),
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
            )
            Text("ss", color = Color.White, modifier = Modifier.padding(top = 8.dp))
        }
    }
}


fun formatTime(pTime: Int): String {
    return String.format("%02d:%02d", pTime / 60, pTime % 60)
}

@Composable
internal fun DisplayTime(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(86.dp)
            .shadow(elevation = 1.dp, shape = CircleShape)
            .background(color = Color(0xFF1B1570), shape = CircleShape)
            .clip(CircleShape), content = content
    )
}

@Composable
internal fun CircleProgress(
    angle: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxSize()
            .padding(82.dp)
            .drawBehind {
                drawArc(
                    color = Color(0xFF080414),
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 30f)
                )
                drawArc(
                    //brush = Brush.verticalGradient(listOf(g1,g2,g3,g4,g5)),
                    color = Color(0xFF9F4ADB),
                    startAngle = -90f,
                    sweepAngle = angle,
                    useCenter = false,
                    style = Stroke(width = 30f, cap = StrokeCap.Round)
                )

                val internalCenter = Offset(size.width / 2, size.width / 2)
                val radius = size.width / 2
                val mThumbX = (internalCenter.x - radius * cos(-(270f - angle) * Math.PI / 180))
                val mThumbY = (internalCenter.y - radius * sin(-(270f - angle) * Math.PI / 180))
                val middle = Offset(mThumbX.toFloat(), mThumbY.toFloat())

                if (angle != 0f) {
                    drawCircle(
                        color = Color(0xFF9F4ADB), radius = 20f,
                        center = middle
                    )

                    drawCircle(
                        color = Color.White, radius = 28f,
                        center = middle,
                        style = Stroke(width = 2f)
                    )
                    drawCircle(
                        color = Color(0xFF9F4AAF), radius = 34f,
                        center = middle,
                        style = Stroke(width = 2f)
                    )
                }

            }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun defPreview() {
    Chrono(seconds = 20, totalTimes = 60f, enabled = false, onSecChange = {}, onMinChange = {})
}