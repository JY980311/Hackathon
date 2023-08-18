package com.example.hackathon.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.size.Size
import com.example.hackathon.R

enum class ButtonType{
    FILL, OUTLINE
}

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.FILL,
    title: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit
){
    when(type){
        ButtonType.FILL -> FilledButton(
            modifier = modifier,
            title = title,
            enabled = enabled,
            isLoading = isLoading,
            onClick = onClick
            )
        ButtonType.OUTLINE -> OutlinedButton(
            modifier = modifier,
            title = title,
            enabled = enabled,
            isLoading = isLoading,
            onClick = onClick
        )
    }
}

@Composable
fun FilledButton(
    modifier: Modifier = Modifier,
    title: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit
){
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color(0xFFEB8C33),
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.DarkGray
        ),
        enabled = enabled,
        onClick = onClick
    ) {
        if(isLoading){
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier
                    .scale(1f)
                    .size(20.dp)
            )
        } else {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Composable
fun OutlinedButton(
    modifier: Modifier = Modifier,
    title: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit
){
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color(0xFFEB8C33),
            disabledContainerColor = Color.White,
            disabledContentColor = Color.LightGray
        ),
        enabled = enabled,
        onClick = onClick
    ) {
        if(isLoading){
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier
                    .scale(1f)
                    .size(20.dp)
            )
        } else {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black, //버튼 안에 있는 글자 색
            containerColor = Color.Transparent //버튼 색
        ),
        border = BorderStroke(0.dp, Color.Transparent), //버튼 테두리
        contentPadding = PaddingValues(0.dp) //버튼 안에 있는 글자와 테두리 사이의 간격
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_backbutton),
            contentDescription = "뒤로가기 버튼",
            modifier = Modifier
                .size(15.dp)
                .padding(start = 2.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = "뒤로가기",
            color = Color.Black,
            fontSize = 15.sp,
        )

    }
}

@Composable
fun AddPostButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black, //버튼 안에 있는 글자 색
            containerColor = Color.LightGray
        ),
        border = BorderStroke(0.dp, Color.Transparent), //버튼 테두리
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = "추가하기 버튼",
            modifier = Modifier
                .size(35.dp)
        )
    }
}
