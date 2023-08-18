package com.example.hackathon.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MyPageViewModel: ViewModel() {
    val selectImages = mutableStateOf<Uri?>(null)
}