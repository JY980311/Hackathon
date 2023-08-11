package com.example.hackathon

import AddPostScreen
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.hackathon.ui.theme.HackathonTheme
import com.example.hackathon.viewmodel.AddPostViewModel

class AddPostActivity: ComponentActivity() {

    private val addPostViewModel: AddPostViewModel by viewModels()

    companion object {
        fun newIntent(context: Context) = Intent(context, AddPostActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(
            WindowManager
                .LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        ) // 키보드가 올라올 때 화면이 잘리는 것을 방지

        setContent {
            HackathonTheme {
                AddPostScreen(addPostViewModel)
            }
        }
    }
}