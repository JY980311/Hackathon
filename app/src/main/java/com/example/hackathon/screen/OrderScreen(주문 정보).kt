package com.example.hackathon.screen

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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle

@Composable
fun OrderScreen (
    routeAction: MainRouteAction
) {

    var rating: Float by remember { mutableStateOf(4.3f) }

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            BackButton(
                onClick = { routeAction.goBack() }
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "판매자 : 주윤발",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Image(
                modifier = Modifier.size(360.dp, 400.dp),
                painter = painterResource(id = R.drawable.ic_tomato_main),
                contentDescription = "토마토 메인사진"
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "손으로 직접 키워서 만든\n토마토 입니다\n정말 신선하고 맛있습니다",
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            RatingBar(
                config = RatingBarConfig()
                    .numStars(5)
                    .size(25.dp)
                    .style(RatingBarStyle.Normal),
                value = rating,
                onValueChange = { rating = it },
                onRatingChanged = {}
            )

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Bottom
            ) {
                Button(
                    onClick = {
                              routeAction.navTo(MainRoute.Order2)
                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFD4DF55),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "주문하기",
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.width(5.dp))

                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFBEBBBB),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "장바구니 담기",
                        fontSize = 20.sp
                    )
                }
            }
        }
    }




