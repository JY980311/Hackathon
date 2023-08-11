package com.example.hackathon.routes

import androidx.navigation.NavHostController

//인증 화면 라우트
enum class AuthRoute(val routeName: String){
    LOGIN("LOGIN"),
    REGISTER("REGISTER")
}

class AuthRouteAction(navHostController: NavHostController) {

    // 특정 라우트로 이동하기
    val navTo: (AuthRoute) -> Unit = {authRoute ->
        navHostController.navigate(authRoute.routeName){
        popUpTo(authRoute.routeName){inclusive = true } //중간에 있는 모든 라우트를 제거하고 이동
        }
    }

    //뒤로가기 이동
    val goBack: () -> Unit = {
        navHostController.navigateUp()
    }
}