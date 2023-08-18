package com.example.hackathon.screen

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.hackathon.R
import com.example.hackathon.routes.MainRoute
import com.example.hackathon.routes.MainRouteAction
import com.example.hackathon.viewmodel.AuthViewModel
import com.example.hackathon.viewmodel.HomeViewModel
import com.example.hackathon.viewmodel.MyPageViewModel
import com.example.hackathon.viewmodel.SearchMyPostViewModel
import kotlinx.coroutines.launch

@Composable
fun MypageScreen(
    homeViewModel: HomeViewModel,
    authViewModel: AuthViewModel,
    searchMyPostViewModel: SearchMyPostViewModel,
    routeAction: MainRouteAction,
    myPageViewModel: MyPageViewModel
) {
    val isRefreshing by homeViewModel.isRefreshing.collectAsState()

    val postsListScrollState = rememberLazyListState()

    //var selectImages by remember { mutableStateOf<Uri?>(null) }
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
       myPageViewModel.selectImages.value = it
    }

    val userId = authViewModel.currentUserIdFlow.collectAsState()
    val userEmail = authViewModel.currentUserEmailFlow.collectAsState()

    val corutineScope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = "나의 정보",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

//            Button(
//                modifier = Modifier
//                    .padding(10.dp)
//                    .size(120.dp),
//                onClick = {
//                    galleryLauncher.launch("image/*")
//                },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.LightGray,
//                )
//
//            ) {
//                Image(
//                    painter =if (selectImages != null) rememberAsyncImagePainter(selectImages)
//                    else painterResource(id = R.drawable.ic_add),
//                    contentDescription = null,
//                    contentScale = ContentScale.FillBounds,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .clip(CircleShape)
//                )
//            }

            IconButton(
                onClick = {
                    galleryLauncher.launch("image/*")
                    Log.d("TAG" , "selectImages: ${myPageViewModel.selectImages.value}")
                },
                modifier = Modifier
                    .size(120.dp)
            ) {
                val selectImages = myPageViewModel.selectImages.value
                Image(
                    painter = if (selectImages != null) rememberAsyncImagePainter(selectImages)
                    else painterResource(id = R.drawable.im_circle_add_button),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            Column {
                Text(text = "이메일: ${userEmail.value}")

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "아이디: ${userEmail.value.split("@")[0]}")
            }
        }


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

        Row() {
            Button( //Button 클릭 안되게 하기 위해서는 onClick = null, enabled = false
                onClick = { null },
                modifier = Modifier
                    .weight(0.5f),
                enabled = false,
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = Color(0xFFEB8C33),
                    disabledContentColor = Color.White
                )
            ) {
                Text(
                    text = "개인적 정보",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = {
                    corutineScope.launch {
                        searchMyPostViewModel.navAction.emit(MainRoute.MyWritePosts)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEB8C33),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .weight(0.5f)
            ) {
                Text(
                    text = "내가 쓴글 보기",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(45.dp))

        Button(
            onClick = {
                      routeAction.navTo(MainRoute.CartList)
            },
            modifier = Modifier.width(320.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD4DF55),
                contentColor = Color.Black
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier.size(30.dp, 30.dp),
                    painter = painterResource(id = R.drawable.ic_cart_icon),
                    contentDescription = "장바구니",
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "담은 장바구니 보러가기",
                    fontSize = 22.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                      routeAction.navTo(MainRoute.MySalesRecord)
            },
            modifier = Modifier.width(320.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF9BF923),
                contentColor = Color.Black
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier.size(30.dp, 30.dp),
                    painter = painterResource(id = R.drawable.ic_receipt_icon),
                    contentDescription = "영수증",
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "나의 판매 기록 보러가기",
                    fontSize = 22.sp
                )
            }
        }
    }
}
