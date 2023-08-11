package com.example.hackathon.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackathon.components.HackathonTextField
import com.example.hackathon.viewmodel.AddPostViewModel
import com.example.hackathon.viewmodel.EditPostViewModel

@Composable
fun EditPostScreen(
    editPostViewModel: EditPostViewModel
) {
    val titleInput = remember { mutableStateOf("") }
    val contentInput = remember { mutableStateOf("") }
    val isAddPostBtnActive = titleInput.value.isNotEmpty()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .verticalScroll(scrollState, enabled = true), // 스크롤 가능하게
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "글 수정하기",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            textAlign = TextAlign.Start,
            style = TextStyle(
                fontSize = 50.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF292929),
            )
        )

        HackathonTextField(
            label = "제목",
            value = titleInput.value,
            onValueChange = {
                titleInput.value = it
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        HackathonTextField(
            modifier = Modifier.height(300.dp),
            label = "내용",
            value = contentInput.value,
            singleLine = false,
            onValueChange = {
                contentInput.value = it
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(7.dp),
            enabled = isAddPostBtnActive,
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),

            ) {
            Text(
                text = "등록", style = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight(400),
                    color = Color.Black,
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}
