package com.example.hackathon.screen
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun OrderScreen2(
    routeAction: MainRouteAction
) {
    var text = remember { mutableStateOf("") }
    var name = remember { mutableStateOf("") }

        Column(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp
            )
        ) {
            BackButton(
                onClick = { routeAction.goBack() }
            )
            Image(
                modifier = Modifier.size(320.dp, 100.dp),
                painter = painterResource(id = R.drawable.ic_tomato_main),
                contentDescription = "토마톻 사진"
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "판매자 : 주윤발\n보내는 곳 : 경기도 안산시\n배송 도착 소요일 : 2~3일",
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column() {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "배송지",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    IconButton(onClick = {
                        routeAction.navTo(MainRoute.AddressSearch)
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "검색"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    color = Color.LightGray,
                    modifier = Modifier
                        .fillMaxWidth(),
                    border = BorderStroke(1.dp, Color.LightGray),
                    shape = RoundedCornerShape(7.dp)
                ) {
                    BasicTextField(
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 20.dp
                        ),
                        value = text.value,
                        onValueChange = { text_value ->
                            text.value = text_value }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column() {
                Text(
                    text = "받는 분 성함",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    color = Color.LightGray,
                    modifier = Modifier
                        .fillMaxWidth(),
                    border = BorderStroke(1.dp, Color.LightGray),
                    shape = RoundedCornerShape(7.dp)
                ) {
                    BasicTextField(
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 20.dp
                        ),
                        value = name.value,
                        onValueChange = {name_value ->
                            name.value = name_value }
                    )
                }

            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "결제수단",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(7.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_kakao_icon),
                        contentDescription = "카카오"
                    )

                    //Spacer(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = "카카오페이로 결제하기",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(7.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_naver_icon),
                        contentDescription = "네이버"
                    )

                    //Spacer(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = "네이버페이로 결제하기",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(7.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_call_icon),
                        contentDescription = "네이버"
                    )

                    //Spacer(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = "전화로 결제하기",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }