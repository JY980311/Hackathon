package com.example.hackathon.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackathon.network.PostRepository
import com.example.hackathon.network.data.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditPostViewModel(val currentPostId: String): ViewModel(){

        companion object {
            const val TAG = "EditPostViewModel"
        }


    var isLoadingFlow = MutableStateFlow<Boolean>(false)

    var isEditLoadingFlow = MutableStateFlow<Boolean>(false)

    var editPostCompleteFlow = MutableSharedFlow<Unit>()
    var titleInputFlow = MutableStateFlow<String>("")
    var contentInputFlow = MutableStateFlow<String>("")

        init {
            Log.d(TAG, "EditPostViewModel : init/ postId: $currentPostId")

            fetchCurrentPostItem()
        }



    fun fetchCurrentPostItem(){
        viewModelScope.launch { callFetchPostItem() }
    }
    fun editPost(){
        viewModelScope.launch { callEditPostItem() }
    }

    private suspend fun callFetchPostItem() { // suspend fun를 사용하는 이유는 비동기로 동작하기 때문에 suspend fun을 사용해야 한다. Flow를 전달할려면 suspend fun 사용
        withContext(Dispatchers.IO) {
            kotlin.runCatching {
                isLoadingFlow.emit(true)
                delay(1500)
                PostRepository.fetchPostItem(currentPostId)
            }.onSuccess {
                titleInputFlow.emit((it.title ?: ""))
                contentInputFlow.emit((it.content ?: ""))
                isLoadingFlow.emit(false)
            }.onFailure {
                Log.d(AddPostViewModel.TAG, "현재 포스트 가져오기 실패 - onFailure error: ${it.localizedMessage}")
                isLoadingFlow.emit(false)
            }
        }
    }

    private suspend fun callEditPostItem() { // suspend fun를 사용하는 이유는 비동기로 동작하기 때문에 suspend fun을 사용해야 한다. Flow를 전달할려면 suspend fun 사용
        withContext(Dispatchers.IO) {
            kotlin.runCatching {
                isEditLoadingFlow.emit(true)
                delay(1500)
                PostRepository.editPostItem(
                    currentPostId,
                    titleInputFlow.value,
                    contentInputFlow.value
                )
            }.onSuccess {
                if (it.status.value == 200) {
                    editPostCompleteFlow.emit(Unit)
                }
                isEditLoadingFlow.emit(false)
            }.onFailure {
                Log.d(AddPostViewModel.TAG, "현재 포스트 가져오기 실패 - onFailure error: ${it.localizedMessage}")
                isEditLoadingFlow.emit(false)
            }
        }
    }

    suspend fun clearInput(){
        titleInputFlow.emit("")
        contentInputFlow.emit("")
    }
}