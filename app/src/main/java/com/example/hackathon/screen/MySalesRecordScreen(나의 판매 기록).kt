package com.example.hackathon.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackathon.R
import com.example.hackathon.components.BackButton
import com.example.hackathon.routes.MainRoute
import com.example.hackathon.routes.MainRouteAction

@Composable
fun MySalesRecordScreen (
    routeAction: MainRouteAction
) {

    val postsListScrollState = rememberLazyListState()

    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        BackButton(
            onClick = { routeAction.goBack()}
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "나의 판매 기록",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                state = postsListScrollState,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(20.dp),
            ) {
                items(10) {
                    LazyMySaleItemView(
                        routeAction = routeAction
                    )
                }
            }
        }
    }
    }

@Composable
fun LazyMySaleItemView(
    routeAction: MainRouteAction
){
    androidx.compose.material3.Surface(
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 8.dp,
        modifier = Modifier
            .size(285.dp, 160.dp),
        color = Color(0xFFDFDFDF)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.im_tomato),
                contentDescription = "토마토 사진",
                modifier = Modifier.size(190.dp, 90.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "2025.05.01",
                    fontSize = 20.sp,
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        routeAction.navTo(MainRoute.CheckReview)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFEB8C33)
                    )
                ) {
                    Text(text = "후기 확인")
                }
            }
        }
    }
}