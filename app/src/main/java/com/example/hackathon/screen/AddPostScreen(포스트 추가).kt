import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackathon.MainActivity
import com.example.hackathon.components.BaseButton
import com.example.hackathon.components.HackathonTextField
import com.example.hackathon.routes.ActivityCLoseAction
import com.example.hackathon.routes.ActivityCloseActionName
import com.example.hackathon.viewmodel.AddPostViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddPostScreen(
    addPostViewModel: AddPostViewModel
) {
    val titleInput = addPostViewModel.titleInputFlow.collectAsState()
    val contentInput = addPostViewModel.contentInputFlow.collectAsState()

    val isAddPostBtnActive = titleInput.value.isNotEmpty()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val isLoading = addPostViewModel.isLoadingFlow.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    val activity = (LocalContext.current as? Activity) // 현재 액티비티 가져오기

    val context = LocalContext.current // 현재 컨텍스트 가져오기

    LaunchedEffect(key1 = Unit, block = {
        addPostViewModel.addPostCompleteFlow.collectLatest {
            snackbarHostState.showSnackbar(
                    "작성 글 등록 완료", actionLabel = "홈으로", SnackbarDuration.Short
                ).let { //액션이 들어 왔을 때
                    when (it) {
                        SnackbarResult.Dismissed -> Log.d("TAG", "스낵바 닫힘")
                        SnackbarResult.ActionPerformed -> {
                            val intent = Intent(
                                context, MainActivity::class.java
                            ).apply {
                                putExtra(ActivityCloseActionName, ActivityCLoseAction.POST_ADDED.actionName)
                            } // 메인 액티비티로 이동 후, 게시글 추가 액션을 보냄
                            activity?.setResult(Activity.RESULT_OK, intent)
                            activity?.finish()
                        }
                    }

                }
        }
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .verticalScroll(scrollState, enabled = true), // 스크롤 가능하게
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "글작성",
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

        HackathonTextField(label = "제목", value = titleInput.value, onValueChange = {
            coroutineScope.launch {
                addPostViewModel.titleInputFlow.emit(it)
            }
        })

        Spacer(modifier = Modifier.height(15.dp))

        HackathonTextField(modifier = Modifier.height(300.dp),
            label = "내용",
            value = contentInput.value,
            singleLine = false,
            onValueChange = {
                coroutineScope.launch {
                    addPostViewModel.contentInputFlow.emit(it)
                }
            })

        Spacer(modifier = Modifier.height(30.dp))

//        Button(
//            onClick = { addPostViewModel.addPost() },
//            modifier = Modifier
//                .fillMaxWidth(),
//            shape = RoundedCornerShape(7.dp),
//            enabled = isAddPostBtnActive,
//            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
//
//            ) {
//            Text(
//                text = "등록", style = TextStyle(
//                    fontSize = 25.sp,
//                    fontWeight = FontWeight(400),
//                    color = Color.Black,
//                )
//            )
//        }
        BaseButton(title = "등록",
            enabled = isAddPostBtnActive,
            isLoading = isLoading.value,
            onClick = {
                if (!isLoading.value) {
                    coroutineScope.launch {
                        addPostViewModel.addPost()
                    }
                }
            })

        Spacer(modifier = Modifier.weight(1f))

        SnackbarHost(snackbarHostState)
    }
}
