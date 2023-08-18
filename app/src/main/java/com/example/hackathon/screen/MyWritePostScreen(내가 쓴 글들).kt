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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackathon.components.DialogAction
import com.example.hackathon.components.SimpleDialog
import com.example.hackathon.network.UserInfo
import com.example.hackathon.network.data.Post
import com.example.hackathon.viewmodel.AuthViewModel
import com.example.hackathon.viewmodel.HomeViewModel
import com.example.hackathon.viewmodel.SearchMyPostViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyWritePostScreen(
    homeViewModel: HomeViewModel,
    searchMyPostViewModel: SearchMyPostViewModel,
    authViewMdoel: AuthViewModel,
) {
    val isRefreshing by searchMyPostViewModel.isRefreshing.collectAsState()

    val isLoading by searchMyPostViewModel.isLoadingFlow.collectAsState() // 로딩중인지

    val postsListScrollState = rememberLazyListState()

    var selectedPostIdForDelete: String? by remember { mutableStateOf(null) }

    val isDialogShown = !selectedPostIdForDelete.isNullOrBlank() // 값이 있으면 다이얼로그 보여주기 없으면 안보여주기

    val coroutineScope = rememberCoroutineScope()

    val posts = searchMyPostViewModel.postsFlow.collectAsState(emptyList())

    LaunchedEffect(key1 = Unit, block = {
        searchMyPostViewModel.dataUpdateFlow.collectLatest {
            selectedPostIdForDelete = null
            postsListScrollState.animateScrollToItem(posts.value.size)
        }
    })
    Column() {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "내가 쓴 글 목록",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {

            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = { searchMyPostViewModel.refreshData() },
            ) {
                LazyColumn(
                    state = postsListScrollState,
                    verticalArrangement = Arrangement.spacedBy(20.dp), // 아이템 간격
                    contentPadding = PaddingValues(20.dp), // 리스트 패딩
                    reverseLayout = true // 아이템 순서를 거꾸로
                ) {
                    items(posts.value) { aPost ->
                        SerchPostItemView(
                            aPost,
                            coroutineScope,
                            searchMyPostViewModel,
                            onDeletePostClicke = {
                                selectedPostIdForDelete = aPost.id.toString()
                            })
                    }
                }
            }

            if (isDialogShown) {
                SimpleDialog(isLoading, onDialogAction = {
                    when (it) {
                        DialogAction.CLOSE -> selectedPostIdForDelete = null
                        DialogAction.ACTION -> {
                            println("아이템 삭제해야함 $selectedPostIdForDelete")
                            selectedPostIdForDelete?.let { postId ->
                                searchMyPostViewModel.deletePostItem(postId)
                            }
                        }

                        else -> {}
                    }
                })
            }
        }
    }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            if (isLoading) {
                Surface(
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.3f)
                        .align(Alignment.Center)
                ) {
                    Box(
                        modifier = Modifier.size(40.dp)
                    ) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .scale(0.7f)
                                .padding(5.dp)
                        )
                    }
                }
            }
        }
}

@Composable
fun SerchPostItemView(
    data: Post,
    coroutineScope: CoroutineScope,
    searchMyPostViewModel: SearchMyPostViewModel,
    onDeletePostClicke: () -> Unit
) {
    //val currentUserId = authViewMdoel.currentUserIdFlow.collectAsState() // 내가 작성한 글만 삭제 해주기 위해
    Surface(
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max), // 높이를 최대로
    ) {
        Column() {

            var userEmail = UserInfo.userEmail.split("@")

            Text(
                text = "userId: ${userEmail[0]}",
                modifier = Modifier
                    .padding(16.dp)
            )

            Row() {
                /*Text(
                    text = "${data.id}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                )*/
                Text(
                    text = "${data.title}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                )
                Row() {
                    TextButton(onClick = {
                        onDeletePostClicke()
                    }) {
                        Text(text = "삭제")
                    }
                }
            }
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
