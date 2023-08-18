package com.example.hackathon.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackathon.components.BackButton
import com.example.hackathon.routes.MainRouteAction

@Composable
fun CheckReviewsScreen (
    routeAction: MainRouteAction
) {
    val postsListScrollState = rememberLazyListState()

    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        BackButton(
            onClick = {
                routeAction.goBack()
            }
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "후기 확인",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                modifier = Modifier
                    .drawBehind {
                        drawLine(
                            color = Color.Black,
                            start = Offset(1f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 1f,
                        )
                    },
                text = "직접 손수 키워 만든 맛있는 가지!",
                fontSize = 25.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                state = postsListScrollState,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(20.dp)
            ){
                items (10)
                {  LazyCheckReviews()}
            }
        }
    }
}



@Composable
fun LazyCheckReviews(){
    androidx.compose.material3.Surface(
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 8.dp,
        modifier = Modifier
            .fillMaxSize(),
        color = Color(0xFFF3EE74)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ){
            Text(
                text = "문의부터 배송까지 친절해요!",
                fontSize = 25.sp
            )

            Spacer(modifier = Modifier.height(10.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "김용명님",
                    fontSize = 18.sp,
                )
            }
        }
    }

}
