package com.example.hackathon.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hackathon.R
import com.example.hackathon.components.BaseButton
import com.example.hackathon.components.HackathonPasswordField
import com.example.hackathon.components.HackathonTextField
import com.example.hackathon.routes.AuthRoute
import com.example.hackathon.routes.AuthRouteAction
import com.example.hackathon.viewmodel.AuthViewModel
import com.example.hackathon.viewmodel.KakaoAuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    //viewModel: KakaoAuthViewModel,
    //onNavigate: () -> Unit
    routeAction: AuthRouteAction,
    authViewModel: AuthViewModel
) {
    val id = authViewModel.emailInputFlow.collectAsState()
    val pw = authViewModel.passwordInputFlow.collectAsState()
    val isLoginBtnActive = id.value.isNotEmpty() && pw.value.isNotEmpty()
    val coroutineScope = rememberCoroutineScope()
    val isLoading = authViewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_image),
            contentDescription = "image description",
            contentScale = ContentScale.FillBounds // 이미지를 꽉 채우기
        )
        Text(
            text = "로그인",
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

//        TextButton(onClick = {
//            coroutineScope.launch{authViewModel.isLoggedIn.emit(true)}
//        }) {
//            Text(
//                text = "로그인 완료"
//            )
//        }

        HackathonTextField(
            label = "이메일 입력",
            value = id.value,
            onValueChange = {
                coroutineScope.launch{
                    authViewModel.emailInputFlow.emit(it)
                }

            }
        )

        HackathonPasswordField(
            label ="비밀번호 입력" ,
            value = pw.value,
            onValueChange = {
                coroutineScope.launch {
                    authViewModel.passwordInputFlow.emit(it)
                }
            }
        )

        TextButton(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.End)) {
            Text(
                text = "비밀번호를 잊어버렸나요?",
                textAlign = TextAlign.End,
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color(0x80000000),
                )
            )
        }
//        Button(
//            onClick = { /*TODO*/ },
//            modifier = Modifier
//                .fillMaxWidth(),
//            shape = RoundedCornerShape(7.dp),
//            enabled = isLoginBtnActive,
//            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEB8C33)),
//
//            ) {
//            Text(
//                text = "계속하기", style = TextStyle(
//                    fontSize = 25.sp,
//                    fontWeight = FontWeight(400),
//                    color = Color(0xFFFFFFFF),
//                )
//            )
//        }
        Spacer(modifier = Modifier.height(25.dp))

        BaseButton(
            title = "계속하기",
            enabled = isLoginBtnActive,
            isLoading = isLoading.value,
            onClick = {
                coroutineScope.launch {
                    authViewModel.login()
                }
            }
        )

        Button(onClick = { /*viewModel.handleKakaoLogin()*/ }) {
            Text(
                text = "카카오 로그인"
            )
        }
        TextButton(onClick = {
        /*onNavigate()*/
            coroutineScope.launch{
                authViewModel.clearInput()
            }
            routeAction.navTo(AuthRoute.REGISTER)
        }) {
            Text(
                text = "회원가입",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color(0x80000000),
                )
            )
        }

    }
}


