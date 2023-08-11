package com.example.hackathon.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackathon.network.UserInfo
import com.example.hackathon.network.UserInfo.userEmail
import com.example.hackathon.routes.MainRouteAction
import com.example.hackathon.viewmodel.AuthViewModel
import com.example.hackathon.viewmodel.HomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun MypageScreen(
    homeViewModel: HomeViewModel,
    authViewModel: AuthViewModel,
    routeAction: MainRouteAction
) {
    val isRefreshing by homeViewModel.isRefreshing.collectAsState()

    val postsListScrollState = rememberLazyListState()

    val userId = authViewModel.currentUserIdFlow.collectAsState()
    val userEmail = authViewModel.currentUserEmailFlow.collectAsState()

    val corutineScope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(text = "마이페이지", fontSize = 30.sp)

        Text(text = "이메일: ")
        Text(text = userId.value)

        Text(text = "아이디: ")
        Text(text = userEmail.value)

        TextButton(
            onClick = {
                corutineScope.launch {
                    authViewModel.isLoggedIn.emit(false)
                }
                authViewModel.clearUserInfo()
            }
        ) {
            Text(text = "로그아웃")
        }
    }


}