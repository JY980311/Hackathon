package com.example.hackathon.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackathon.network.AuthRepository
import com.example.hackathon.network.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddPostViewModel: ViewModel() {

    companion object{
        const val TAG = "AddPostViewModel"
    }

    var isLoadingFlow = MutableStateFlow<Boolean>(false)

    var addPostCompleteFlow = MutableSharedFlow<Unit>()
    var titleInputFlow = MutableStateFlow<String>("")
    var contentInputFlow = MutableStateFlow<String>("")

    fun addPost(){
        viewModelScope.launch { callAddPost() }
    }

    private suspend fun callAddPost(){ // suspend fun를 사용하는 이유는 비동기로 동작하기 때문에 suspend fun을 사용해야 한다. Flow를 전달할려면 suspend fun 사용
        withContext(Dispatchers.IO){
            kotlin.runCatching {
                isLoadingFlow.emit(true)
                delay(1500)
                PostRepository.addPostItem(
                    titleInputFlow.value,
                    contentInputFlow.value)
            }.onSuccess {
                if(it.status.value == 201){
                    addPostCompleteFlow.emit(Unit)
                }
                clearInput()
                isLoadingFlow.emit(false)
            }.onFailure {
                Log.d(TAG, "포스트 등록 실패 - onFailure error: ${it.localizedMessage}")
                isLoadingFlow.emit(false)
            }
        }
    }

    suspend fun clearInput(){ // suspend를 사용하는 이유는 비동기로 동작하기 때문에 suspend fun을 사용해야 한다.
        titleInputFlow.emit("")
        contentInputFlow.emit("")
    }
}