package com.example.hackathon

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hackathon.routes.ActivityCLoseAction
import com.example.hackathon.routes.AuthRoute
import com.example.hackathon.routes.AuthRouteAction
import com.example.hackathon.routes.MainRoute
import com.example.hackathon.routes.MainRouteAction
import com.example.hackathon.screen.HomeScreen
import com.example.hackathon.screen.LoginScreen
import com.example.hackathon.screen.MypageScreen
import com.example.hackathon.screen.RegisterScreen
import com.example.hackathon.ui.theme.HackathonTheme
import com.example.hackathon.viewmodel.AuthViewModel
import com.example.hackathon.viewmodel.HomeViewModel
import com.example.hackathon.viewmodel.KakaoAuthViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val kakaoAuthViewModel: KakaoAuthViewModel by viewModels()

    private val authViewModel: AuthViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    //액티비티가 닫아질 때 이벤트
    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val getActionString = result.data?.getStringExtra("CLOSE_ACTION")

            //ActivityCLoseAction에 상관없는 애들이 들어오면 null 값이다
            val closeAction : ActivityCLoseAction? = ActivityCLoseAction.getActionType(getActionString?:"") // getActionString이 null이면 ""을 넣어줌

            closeAction?.let{ // 값이 있을 때만 let 블럭을 실행
                homeViewModel.refreshData()
            }

//            if (getActionString == "POST_ADDED") {
//                homeViewModel.refreshData()
//            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(
            WindowManager
                .LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                ) // 키보드가 올라올 때 화면이 잘리는 것을 방지


        lifecycleScope.launch {
            homeViewModel.navAction.collectLatest {
                when(it) {
                    is MainRoute.AddPost -> {
                        //startActivity(AddPostActivity.newIntent(this@MainActivity))
                        activityResultLauncher.launch(AddPostActivity.newIntent(this@MainActivity))
                    }
                    is MainRoute.EditPost -> {
                        //startActivity(EditPostActivity.newIntent(this@MainActivity,it.postId))
                        val intent = EditPostActivity.newIntent(this@MainActivity,it.postId)
                        activityResultLauncher.launch(intent)
                    }

                    else -> {}
                }
            }
        }

        setContent {
            HackathonTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background // 수정부분
                ) {
                    // LoginScreen(kakaoAuthViewModel)
//                    val nevController = rememberNavController()
//                    NavigationGraph(navController = navController)
                    AppScreen(authViewModel, homeViewModel)
                }
            }
        }
    }
}

@Composable
fun BottomNav(
    mainRouteAction: MainRouteAction,
    mainRouteBackStackEntry: NavBackStackEntry?
) {
    val bottomRoutes = listOf<MainRoute>(MainRoute.Home, MainRoute.Mypage)
    BottomNavigation(
        backgroundColor = Color.LightGray,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        bottomRoutes.forEach {
            BottomNavigationItem(
                label = { Text(text = it.title) },
                icon = {
                    it.iconResId?.let { iconId ->
                        Icon(
                            painter = painterResource(id = iconId),
                            contentDescription = it.title,
                            //tint = Color.White

                        )
                    }
                },

                unselectedContentColor = Color.Black, // 선택되지 않았을 때의 색상
                selectedContentColor = Color.White, // 선택되었을 때의 색상
                selected = (mainRouteBackStackEntry?.destination?.route) == it.routeName,// 현재 선택된 라우트와 같은지 비교
                onClick = { mainRouteAction.navTo(it) } // 클릭 시 라우트 이동
            )


        }
    }
}


@Composable
fun AuthNavHost(
    authNavController: NavHostController,
    startRouter: AuthRoute = AuthRoute.LOGIN,
    authViewModel: AuthViewModel,
    routeAction: AuthRouteAction
) {
    NavHost(
        navController = authNavController,
        startDestination = startRouter.routeName
    ) {
        composable(AuthRoute.LOGIN.routeName) {
            LoginScreen(routeAction, authViewModel)
        }
        composable(AuthRoute.REGISTER.routeName) {
            RegisterScreen( authViewModel,routeAction)
        }
    }
}

@Composable
fun MainNavHost(
    mainNavController: NavHostController,
    startRouter: MainRoute = MainRoute.Home,
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel,
    routeAction: MainRouteAction
) {
    NavHost(
        navController = mainNavController,
        startDestination = startRouter.routeName
    ) {
        composable(MainRoute.Home.routeName) {
            HomeScreen(homeViewModel,authViewModel, routeAction )
        }
        composable(MainRoute.Mypage.routeName) {
            MypageScreen(homeViewModel, authViewModel, routeAction )
        }
    }
}


@Composable
fun AppScreen(
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel
) {

    val isLoggedIn = authViewModel.isLoggedIn.collectAsState()

    val authNavController = rememberNavController()

    val authRouteAction = remember(authNavController) {
        AuthRouteAction(authNavController)
    }

    val mainNavController = rememberNavController()

    val mainRouteAction = remember(mainNavController) {
        MainRouteAction(mainNavController)
    }

    val authBackstackEntry = authNavController.currentBackStackEntry // 현재 백스택 엔트리를 가져온다.

    val mainBackstackEntry = mainNavController.currentBackStackEntry // 현재 백스택 엔트리를 가져온다.

    if (!isLoggedIn.value) {
        AuthNavHost(
            authNavController = authNavController,
            authViewModel = authViewModel,
            routeAction = authRouteAction
        )
    } else {

        Scaffold(bottomBar = {
            BottomNav(mainRouteAction, mainBackstackEntry)
        })
        {
            Column(
                modifier = Modifier.padding(bottom = it.calculateBottomPadding())
            ) {
                MainNavHost(
                    mainNavController = mainNavController,
                    authViewModel = authViewModel,
                    homeViewModel = homeViewModel,
                    routeAction = mainRouteAction
                )
            }
        }
    }
}