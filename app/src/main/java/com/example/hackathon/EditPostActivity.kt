package com.example.hackathon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.hackathon.screen.EditPostScreen
import com.example.hackathon.ui.theme.HackathonTheme
import com.example.hackathon.viewmodel.EditPostViewModel

class EditPostActivity : ComponentActivity() {

    lateinit var editPostViewModel: EditPostViewModel
    companion object {
        private const val POST_ID="post_Id"
        fun newIntent(context: Context, postId:String) =
            Intent(context, EditPostActivity::class.java).apply{
                putExtra(POST_ID, postId)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(
            WindowManager
                .LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        ) // 키보드가 올라올 때 화면이 잘리는 것을 방지

        val postId = intent.getStringExtra(POST_ID) ?: ""

        editPostViewModel = EditPostViewModel(postId)

        setContent {
            HackathonTheme {
                EditPostScreen(editPostViewModel)
            }
        }
    }
}