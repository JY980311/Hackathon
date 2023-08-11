package com.example.hackathon.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackathon.components.AddPostButton
import com.example.hackathon.components.DialogAction
import com.example.hackathon.components.SimpleDialog
import com.example.hackathon.network.data.Post
import com.example.hackathon.routes.AuthRouteAction
import com.example.hackathon.routes.MainRoute
import com.example.hackathon.routes.MainRouteAction
import com.example.hackathon.viewmodel.HomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    routeAction: MainRouteAction
) {
    val isRefreshing by homeViewModel.isRefreshing.collectAsState()

    val isLoading by homeViewModel.isLoadingFlow.collectAsState() // 로딩중인지

    val postsListScrollState = rememberLazyListState()

    var selectedPostIdForDelete: String? by remember { mutableStateOf(null) }

    val isDialogShown = !selectedPostIdForDelete.isNullOrBlank() //값이 있으면 다이얼로그 보여주기 없으면 안보여주기

    val coroutineScope = rememberCoroutineScope()

    val posts = homeViewModel.postsFlow.collectAsState(emptyList())

    LaunchedEffect(key1 = Unit, block = {
        homeViewModel.dataUpdateFlow.collectLatest {
            postsListScrollState.animateScrollToItem(posts.value.size)
        }
    } )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { homeViewModel.refreshData() },
        ) {
            LazyColumn(
                state = postsListScrollState,
                verticalArrangement = Arrangement.spacedBy(20.dp), // 아이템 간격
                contentPadding = PaddingValues(20.dp), // 리스트 패딩
                reverseLayout = true // 아이템 순서를 거꾸로
            ) {
                items(posts.value){ aPost ->
                    PostItemView(aPost,
                        coroutineScope,
                        homeViewModel,
                        onDeletePostClicke = {
                        selectedPostIdForDelete = aPost.id.toString()
                    })
                }
            }
        }

        AddPostButton(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.BottomEnd) // 오른쪽 아래에 위치 박스에서는 가능
                ,
            onClick ={
                coroutineScope.launch {
                    homeViewModel.navAction.emit(MainRoute.AddPost)
                }
            }
        )

        if(isLoading){
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier
                    .scale(0.7f)
                    .padding(5.dp)
            )
        }

        if(isDialogShown){
            SimpleDialog(isLoading, onDialogAction = {
                when(it) {
                    DialogAction.CLOSE -> selectedPostIdForDelete = null
                    DialogAction.CLOSE -> {
                        println("아이템 삭제해야함 $selectedPostIdForDelete")
                    }

                    else -> {}
                }
            })
        }
    }
}

@Composable
fun PostItemView(
    data: Post,
    coroutineScope: CoroutineScope,
    homeViewModel: HomeViewModel,
    onDeletePostClicke: () -> Unit
){
    Surface(
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Column() {

            Text(text = "userId: ${data.userID}")

            Row() {
                Text(
                    text = "${data.id}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                )

                TextButton(onClick = {
                    onDeletePostClicke()
                }) {
                    Text(text = "삭제")
                }

                TextButton(onClick = {
                    coroutineScope.launch {
                    homeViewModel.navAction.emit(MainRoute.EditPost(postId = "${data.id}"))
                }}) {
                    Text(text = "수정")
                }

            }
            Text(
                text = "${data.title}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "${data.content}",
                maxLines = 5,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            )
        }
    }
}

