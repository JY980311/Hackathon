package com.example.hackathon.network

import com.example.hackathon.network.data.AuthRequest
import com.example.hackathon.network.data.AuthResponse
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

object AuthRepository { //리포지토리는 데이터를 가져오는 역할을 한다.

    private const val registerUrl = "https://xprcttldodtohpjogxmz.supabase.co/auth/v1/signup"
    private const val  loginUrl = "https://xprcttldodtohpjogxmz.supabase.co/auth/v1/token"

    private const val TAG: String = "AuthRepository"

    suspend fun register(email:String, password: String) : HttpResponse {

        return KtorClient.httpClient.post(registerUrl){
            setBody(AuthRequest(email, password))
        }
    }

    suspend fun login(email:String, password: String) : HttpResponse {

        return KtorClient.httpClient.post(loginUrl){
            url {
                parameters.append("grant_type", "password") // password를 통해 토큰을 받는다.
            }
            setBody(AuthRequest(email, password))
        }
    }
}