/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.components.Chrono
import com.example.androiddevchallenge.components.Controllers
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.SystemUiController
import com.example.androiddevchallenge.ui.theme.bgEdgeColor
import com.example.androiddevchallenge.viewmodels.TViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<TViewModel>()
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            MyTheme {
                systemUiController.setSystemBarsColor(color = Color(0xFF1B1570))
                MyApp(viewModel)
            }
        }
    }
}

// Start building your app here!
@ExperimentalAnimationApi
@Composable
fun MyApp(tViewModel: TViewModel) {
    val times by tViewModel.times.collectAsState()
    var totalTimes by remember { mutableStateOf(60) }
    var min by remember { mutableStateOf(1) }
    var sec by remember { mutableStateOf(0) }
    var isEnabled by remember { mutableStateOf(false) }
    Scaffold(
       topBar = {
           TopAppBar(
               backgroundColor =Color(0xFF1B1570),
               elevation = 0.dp,
               title = {
                   Text(text = "Timer", color=  Color.White)
               }
           )
       }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                //.background(Color(0xFF1B1570))
                //.background(Color(0xFFf5b550))
                .background(Brush.radialGradient(listOf(bgEdgeColor, Color(0xFF1B1570)))),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Chrono(seconds = times, totalTimes = totalTimes.toFloat(),
                    enabled = isEnabled,
                    onMinChange = { m ->
                        run {
                            min = m
                            totalTimes = min * 60 + sec
                        }
                    }, onSecChange = { s ->
                        run {
                            sec = s
                            totalTimes = min * 60 + sec
                        }
                    })
                Controllers(onPause = { tViewModel.pause() },
                    onStart = {
                    tViewModel.start(totalTimes)
                    if(!isEnabled) isEnabled = true
                }, onStop = {
                    tViewModel.stop()
                    isEnabled = !isEnabled
                })
            }
        }
    }

}


@ExperimentalAnimationApi
@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp(TViewModel())
    }
}

@ExperimentalAnimationApi
@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp(TViewModel())
    }
}
