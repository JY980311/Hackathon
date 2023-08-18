package com.example.hackathon.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackathon.R
import com.example.hackathon.components.BackButton
import com.example.hackathon.routes.MainRouteAction

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SalesRegistrationScreen(
    routeAction: MainRouteAction
) {

    val text = remember { mutableStateOf("") }
    val content_text = remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier.padding(
            start = 20.dp,
            end = 20.dp
        ),
        horizontalAlignment = Alignment.Start
    ) {
        BackButton(
            onClick = {
                routeAction.goBack()
            }
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "판매등록",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Column()
        {
//            TextField(
//                value = value ,
//                onValueChange = {text},
//                textStyle = TextStyle(
//                    fontSize = 25.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Black
//                ),
//                colors = TextFieldDefaults.textFieldColors(
//                    backgroundColor = Color.Transparent
//                ),
//                placeholder = {
//                    Text(
//                        text = "제목",
//                        fontSize = 25.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.Black
//                    )
//                }
//            )
            Spacer(modifier = Modifier.height(25.dp))

            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        drawLine(
                            color = Color.Black,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 1f
                        )
                    },
                value = text.value,
                onValueChange = { text_value -> text.value = text_value }
            ) { innerTextField ->
                Text(
                    text = "제목",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                TextFieldDefaults.TextFieldDecorationBox(
                    value = text.value,
                    innerTextField = { text.value },
                    enabled = true,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,//
                    interactionSource = interactionSource,//
                    contentPadding = PaddingValues(0.dp),
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "내용",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
            )
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .height(165.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.LightGray
                )
            ) {
                Image(
                    modifier = Modifier.size(30.dp, 30.dp),
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "더하기"
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "추가설명",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            BasicTextField(
                value = content_text.value,
                onValueChange = { content_value -> content_text.value = content_value },
                modifier = Modifier
                    .height(165.dp)
                    .fillMaxWidth()
                    .border(1.5f.dp, Color.LightGray, RoundedCornerShape(0.dp))
            )

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .size(150.dp, 55.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFEB8C33),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "등록",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

