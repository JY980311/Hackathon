package com.example.hackathon.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.hackathon.R
import com.example.hackathon.routes.MainRoute
import com.example.hackathon.routes.MainRouteAction
import com.example.hackathon.ui.theme.MyColor
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle


@Composable
fun HomeScreen(
    routeAction: MainRouteAction
) {

    val postsListScrollState = rememberLazyListState()
    val text_input = remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Column {
        TextField(
            value = text_input.value,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            onValueChange = {  text_value -> text_input.value = text_value },
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(16.dp)
                .border(
                    border = BorderStroke(1.dp, Color.LightGray),
                    shape = RoundedCornerShape(7.dp)
                ), //
            shape = RoundedCornerShape(7.dp),
            singleLine = true,
            placeholder = { Text(text = "검색하세요") },
            leadingIcon = { Icon(
                painter = painterResource(id = R.drawable.ic_search) ,
                contentDescription = "검색")
            }
        )

        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "찾아보세요",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
            )
            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = "더보기",
                    color = Color.LightGray
                )
            }
        }

        LazyColumn(
            modifier = Modifier.height(365.dp),
            state = postsListScrollState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(20.dp),
        )
        {
            items(10) {
                LazyItemView(
                    routeAction
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "판매를 원하시나요?",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                          routeAction.navTo(MainRoute.SalesRegistration)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFD4DF55)
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(0.7f),
                        painter = painterResource(id = R.drawable.ic_cart_icon),
                        contentDescription = "카트",
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "등록하러 가기",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(3.dp))

            Button(
                onClick = {
                          routeAction.navTo(MainRoute.MySalesRecord)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF9BF923)
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(10.dp))

                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(0.7f),
                        painter = painterResource(id = R.drawable.ic_receipt_icon),
                        contentDescription = "영수증",
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "판매기록 보기",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}


@Composable
fun LazyItemView(
    routeAction: MainRouteAction
) {
    var rating: Float by remember { mutableStateOf(4.3f) }

    val navController = rememberNavController() //

    val coroutineScope = rememberCoroutineScope()

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
                RatingBar(
                    config = RatingBarConfig()
                        .numStars(5)
                        .size(16.dp)
                        .style(RatingBarStyle.Normal),
                    value = rating,
                    onValueChange = { rating = it },
                    onRatingChanged = {}
                )

                Text(
                    text = "(99+)",
                    modifier = Modifier.padding(start = 8.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                           routeAction.navTo(MainRoute.Order)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFEB8C33),
                        contentColor = Color.White
                    )
                ){
                    Text(text = "주문하기")
                }
            }
        }
    }
}
