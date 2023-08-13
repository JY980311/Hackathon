package com.example.hackathon.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackathon.network.AuthRepository
import com.example.hackathon.network.PostRepository
import com.example.hackathon.network.data.Post
import com.example.hackathon.routes.MainRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel: ViewModel() {

    val isRefreshing = MutableStateFlow<Boolean>(false)

    val isLoadingFlow = MutableStateFlow<Boolean>(false)

    val postsFlow = MutableStateFlow<List<Post>>(emptyList()) // post를 가져오기 위한 flow

    var navAction = MutableSharedFlow<MainRoute>()

    var dataUpdateFlow = MutableSharedFlow<Unit>() // 값이 있는건 아니라서 Unit으로 지정



    //MutableSharedFlow와 MutableStateFlow의 차이는 MutableSharedFlow는 데이터를 여러 번 emit할 수 있지만 MutableStateFlow는 데이터를 한 번만 emit할 수 있다.

    companion object {
        const val TAG = "HomeViewModel"
    }

    init {
        fetchPosts()
    }

    fun refreshData(){
        Log.d(TAG, "refreshData: ")
        viewModelScope.launch{
            isLoadingFlow.emit(true)
            delay(1000)
            callFetchPosts()
        }
    }

    fun fetchPosts(){
        viewModelScope.launch{
            callFetchPosts()
        }
    }

    fun deletePostItem(postId : String) {
        viewModelScope.launch {
            callDeletePostItem(postId)
        }
    }

    private suspend fun callFetchPosts(){ // suspend fun를 사용하는 이유는 비동기로 동작하기 때문에 suspend fun을 사용해야 한다. Flow를 전달할려면 suspend fun 사용
        withContext(Dispatchers.IO){
            kotlin.runCatching {
                isLoadingFlow.emit(true)
                delay(1500)
                PostRepository.fetchAllPost()
            }.onSuccess {
                postsFlow.emit(it)
                dataUpdateFlow.emit(Unit)
                isLoadingFlow.emit(false)
            }.onFailure {
                Log.d(TAG, " 실패 - onFailure error: ${it.localizedMessage}")
                isLoadingFlow.emit(false)
            }
        }
    }

    private suspend fun callDeletePostItem(PostId: String){ // suspend fun를 사용하는 이유는 비동기로 동작하기 때문에 suspend fun을 사용해야 한다. Flow를 전달할려면 suspend fun 사용
        withContext(Dispatchers.IO){
            kotlin.runCatching {
                PostRepository.deletePostItem(PostId)
            }.onSuccess {
                if (it.status.value == 204) {
                    refreshData()
                }
                isLoadingFlow.emit(false)
            }.onFailure {
                Log.d(TAG, " 실패 - onFailure error: ${it.localizedMessage}")
                isLoadingFlow.emit(false)
            }
        }
    }
}