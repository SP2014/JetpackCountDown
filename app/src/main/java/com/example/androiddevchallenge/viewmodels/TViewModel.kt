package com.example.androiddevchallenge.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.isActive
import kotlinx.coroutines.Job
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TViewModel: ViewModel() {
    private var job: Job? = null
    private val _times = MutableStateFlow(0)
    val times = _times.asStateFlow()

    fun start(times: Int = 30){
        if(_times.value == 0) _times.value = times
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            while(isActive){
                if(_times.value <= 0){
                    job?.cancel()
                    return@launch
                }
                delay(timeMillis = 1000)
                _times.value -= 1
            }
        }
    }

    fun pause(){
        job?.cancel()
    }

    fun stop(){
        job?.cancel()
        _times.value = 0
    }
}