package com.example.hackathon.screen.authscreen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackathon.R
import com.example.hackathon.components.BackButton
import com.example.hackathon.components.BaseButton
import com.example.hackathon.components.HackathonPasswordField
import com.example.hackathon.components.HackathonTextField
import com.example.hackathon.routes.AuthRoute
import com.example.hackathon.routes.AuthRouteAction
import com.example.hackathon.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    //onPopBackStack: () -> Unit
    authViewModel: AuthViewModel, routeAction: AuthRouteAction
) {
    val emailInput = authViewModel.emailInputFlow.collectAsState()
    val passwordInput = authViewModel.passwordInputFlow.collectAsState()
    val isRegisterBtnActive = emailInput.value.isNotEmpty() && passwordInput.value.isNotEmpty()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val isLoading = authViewModel.isLoading.collectAsState().value

    LaunchedEffect(key1 = Unit, block = {
        // 회원가입 성공시 이벤트
        authViewModel.registerCompleteFlow.collectLatest {  // collectLatest는 Flow의 최신 값을 수신한다.
            snackbarHostState.showSnackbar(
                    "회원가입이 완료되었습니다. 로그인 해주세요.", actionLabel = "확인", SnackbarDuration.Short
                ).let { // let은 블록 내부에서 수신 객체를 람다의 인자로 전달하고 람다의 결과값을 반환한다.
                    when (it) {
                        SnackbarResult.Dismissed -> Log.d("TAG", "스낵바 닫힘")
                        SnackbarResult.ActionPerformed -> {
                            routeAction.navTo(AuthRoute.LOGIN)
                        }
                    }

                }
        }
    }) // LaunchedEffect는 컴포즈블이 처음 그려질 때 실행된다, 생성 되었을 때 실행된다.

    BackButton(
        onClick = {
        /*onPopBackStack*/
        routeAction.goBack()
    })

    Column(
        modifier = Modifier.padding(horizontal = 30.dp),
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.im_login_image),
            contentDescription = "회원가입",

            )

        Text(
            text = "회원가입",
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

        Column(
        ) {
//            Surface(
//                color = Color.LightGray,
//                modifier = Modifier.fillMaxWidth(),
//                border = BorderStroke(1.dp, Color.LightGray),
//                shape = RoundedCornerShape(7.dp)
//            ) {
//                BasicTextField(
//                    modifier = Modifier.padding(
//                        horizontal = 16.dp,
//                        vertical = 20.dp
//                    ),
//                    value = emailInput.value,
//                    onValueChange = {
//                        coroutineScope.launch {
//                            authViewModel.emailInputFlow.emit(it) // emit은 Flow의 값을 변경한다.
//                        }
//                    })
//            }
            HackathonTextField(label = "아이디 입력", value = emailInput.value, onValueChange = {
                coroutineScope.launch {
                    authViewModel.emailInputFlow.emit(it)
                }

            })
        }

//        Column() {
//            Text(text = "비밀번호 입력")
//            Surface(
//                color = Color.LightGray,
//                modifier = Modifier.fillMaxWidth(),
//                border = BorderStroke(1.dp, Color.LightGray),
//                shape = RoundedCornerShape(7.dp)
//            ) {
//                BasicTextField(modifier = Modifier.padding(
//                    horizontal = 16.dp, vertical = 20.dp
//                ), value = passwordInput.value, onValueChange = {
//                    coroutineScope.launch {
//                        authViewModel.passwordInputFlow.emit(it)
//                    }
//                })
//            }
//        }
        HackathonPasswordField(
            label ="비밀번호 입력" ,
            value = passwordInput.value,
            onValueChange = {
                coroutineScope.launch {
                    authViewModel.passwordInputFlow.emit(it)
                }
            }
        )

//        Button(
//            onClick = {
//                      coroutineScope.launch{
//                          authViewModel.register() // register에서 이미 viewModelScope.launch를 사용했기 때문에 여기서는 코루틴으로 감싸지 않아도 된다
//                      }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 20.dp),
//            shape = RoundedCornerShape(7.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEB8C33))
//        ) {
//            Text(text = "회원가입")
//        }

        Spacer(modifier = Modifier.height(25.dp))

        BaseButton(
            title = "회원가입", enabled = isRegisterBtnActive, onClick = {
                if (!isLoading) {
                    coroutineScope.launch {
                        authViewModel.register()
                    }
                }
            }, isLoading = isLoading
        )

        TextButton(
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                /*onPopBackStack()*/
                coroutineScope.launch { authViewModel.clearInput() }
                routeAction.navTo(AuthRoute.LOGIN)
            },
            colors = ButtonDefaults.textButtonColors(contentColor = Color.Black),
        ) {
            Text("이미 계정이 있으신가요?", fontWeight = FontWeight.Bold)
        }

        SnackbarHost(hostState = snackbarHostState)
    }
}