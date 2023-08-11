package com.example.hackathon.routes

import androidx.navigation.NavHostController
import com.example.hackathon.R

const val ActivityCloseActionName = "CLOSE_ACTION"

enum class ActivityCLoseAction(val actionName: String){ // 액션의 이름을 저장
    POST_ADDED("POST_ADDED"),
    POST_EDIT("POST_EDIT"),
    POST_DELETED("POST_DELETED");

    companion object{ // 액션의 이름을 받아서 해당 액션을 반환하는 헬퍼 메소드

        fun getActionType(name: String) : ActivityCLoseAction? {
            when(name){ // 이름이 같으면 해당 액션을 반환
                POST_ADDED.actionName -> return POST_ADDED
                POST_EDIT.actionName -> return POST_EDIT
                POST_DELETED.actionName -> return POST_DELETED
                else -> return null
            }
        }
    }


}

sealed class MainRoute(
    open val routeName: String,
    open val title: String,
    val iconResId: Int? = null
){
    object Home: MainRoute("HOME", "홈", R.drawable.ic_home)
    object Mypage: MainRoute("MY_PAGE", "마이페이지", R.drawable.ic_user)
    object AddPost: MainRoute("ADD_POST", "게시글 작성")
    class EditPost(val postId:String): MainRoute("EDIT_POST", "게시글 수정")

}

//메인 관련 화면 라우트 액션
class MainRouteAction(navHostController: NavHostController) {

    // 특정 라우트로 이동하기
    val navTo: (MainRoute) -> Unit = {route ->
        navHostController.navigate(route.routeName){
            popUpTo(route.routeName){inclusive = true } //중간에 있는 모든 라우트를 제거하고 이동
        }
    }

    //뒤로가기 이동
    val goBack: () -> Unit = {
        navHostController.navigateUp()
    }
}