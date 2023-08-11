package com.example.hackathon.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackathon.network.AuthRepository
import com.example.hackathon.network.UserInfo
import com.example.hackathon.network.data.AuthResponse
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel: ViewModel() {

    companion object{
        const val TAG = "AuthViewModel"
    }

    var isLoggedIn = MutableStateFlow<Boolean>(false)
    var isLoading = MutableStateFlow<Boolean>(false)

    var registerCompleteFlow = MutableSharedFlow<Unit>() // 회원가입 완료 후 로그인 화면으로 이동하기 위한 flow 한 번만 수행하기 위해서 SharedFlow을 사용했고 데이터가 없기 때문에 Unit으로 지정
    var emailInputFlow = MutableStateFlow<String>("")
    var passwordInputFlow = MutableStateFlow<String>("")

    var currentUserEmailFlow = MutableStateFlow<String>("") // 회원가입 완료 후 로그인 화면으로 이동하기 위한 flow 한 번만 수행하기 위해서 SharedFlow을 사용했고 데이터가 없기 때문에 Unit으로 지정
    var currentUserIdFlow = MutableStateFlow<String>("")


    fun register(){
        viewModelScope.launch { callRegister() }
    }

    fun login(){
        viewModelScope.launch { callLogin() }
    }

    private suspend fun callRegister(){ // suspend fun를 사용하는 이유는 비동기로 동작하기 때문에 suspend fun을 사용해야 한다. Flow를 전달할려면 suspend fun 사용
        withContext(Dispatchers.IO){
            kotlin.runCatching {
                isLoading.emit(true)
                delay(1500)
                AuthRepository.register(emailInputFlow.value,
                                        passwordInputFlow.value)
            }.onSuccess {
                if(it.status.value == 200){
                    registerCompleteFlow.emit(Unit)
                }
               clearInput()
                isLoading.emit(false)
            }.onFailure {
                Log.d(TAG, "로그인 실패 - onFailure error: ${it.localizedMessage}")
                isLoading.emit(false)
            }
        }
    }

    private suspend fun callLogin(){ // suspend fun를 사용하는 이유는 비동기로 동작하기 때문에 suspend fun을 사용해야 한다. Flow를 전달할려면 suspend fun 사용
        withContext(Dispatchers.IO){
            kotlin.runCatching {
                isLoading.emit(true)
                delay(1500)
                AuthRepository.login(emailInputFlow.value,
                    passwordInputFlow.value)
            }.onSuccess {
                if(it.status.value == 200){
                    isLoggedIn.emit(true)
                }
                val authResponse = it.body<AuthResponse>()

                UserInfo.accessToken = authResponse.accessToken ?: ""
                UserInfo.userEmail = authResponse.user?.email ?: ""
                UserInfo.userId = authResponse.user?.id ?: ""

                currentUserEmailFlow.emit(UserInfo.userEmail)
                currentUserIdFlow.emit(UserInfo.userId)

                clearInput()
                isLoading.emit(false)
            }.onFailure {
                Log.d(TAG, "회원가입 실패 - onFailure error: ${it.localizedMessage}")
                isLoading.emit(false)
            }
        }
    }


     suspend fun clearInput(){ // suspend를 사용하는 이유는 비동기로 동작하기 때문에 suspend fun을 사용해야 한다.
        emailInputFlow.emit("")
        passwordInputFlow.emit("")
    }

    fun clearUserInfo(){
        UserInfo.clearData()
    }
}