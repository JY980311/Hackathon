//package com.example.hackathon.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import com.example.hackathon.screen.LoginScreen
//import com.example.hackathon.screen.RegisterScreen
//import com.example.hackathon.viewmodel.KakaoAuthViewModel
//
//@Composable
//fun NavigationGraph(navController: NavHostController) {
//
//    val viewModel = viewModel<KakaoAuthViewModel>()
//
//    NavHost(
//        navController = navController,
//        startDestination = Routes.LOGIN_SCREEN
//    ) {
//        composable(route = Routes.LOGIN_SCREEN) {
//            LoginScreen(
//                //viewModel = viewModel,
//                //onNavigate = { navController.navigate(Routes.REGISTER_SCREEN) }
//
//            )
//        }
//
//        composable(route = Routes.REGISTER_SCREEN) {
//            RegisterScreen(
//                //onPopBackStack = { navController.popBackStack() }
//            )
//        }
//    }
//}

