package com.example.hackathon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.hackathon.screen.MyWritePostScreen
import com.example.hackathon.ui.theme.HackathonTheme
import com.example.hackathon.viewmodel.AuthViewModel
import com.example.hackathon.viewmodel.HomeViewModel
import com.example.hackathon.viewmodel.SearchMyPostViewModel

class MyWriteActivity: ComponentActivity(){

    private val homeViewModel: HomeViewModel by viewModels()
    private val searchMyPostViewModel : SearchMyPostViewModel by viewModels()
    private val authViewModel : AuthViewModel by viewModels()

    companion object {
        fun newIntent(context: Context) = Intent(context, MyWriteActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setContent {
            HackathonTheme {
                MyWritePostScreen(homeViewModel,searchMyPostViewModel,authViewModel)
            }
        }
    }

}