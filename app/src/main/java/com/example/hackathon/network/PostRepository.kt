package com.example.hackathon.network

import com.example.hackathon.BuildConfig
import com.example.hackathon.network.data.AuthRequest
import com.example.hackathon.network.data.Post
import com.example.hackathon.network.data.PostRequest
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

object PostRepository {

    private const val postUrl = "https://xprcttldodtohpjogxmz.supabase.co/rest/v1/posts"

    private const val TAG: String = "AuthRepository"

    //모든 포스트 가져오기
    suspend fun fetchAllPost() : List<Post> {

        return KtorClient.httpClient.get(postUrl){
            url {
                parameters.append("select", "*") // password를 통해 토큰을 받는다.
            }
            headers{
                headers.append("Authorization", "Bearer ${BuildConfig.SUPERBASE_KEY}")
            }
        }.body<List<Post>>()
    }

    //단일 포스트 가져오기
    suspend fun fetchPostItem(postId: String) : Post { //postId를 받아서 Post를 반환한다. 1개만 반환한다해서 List대신 단일 Post를 사용

        return KtorClient.httpClient.get(postUrl){
            url {
                parameters.append("select", "*") // password를 통해 토큰을 받는다.
                parameters.append("id", "eq.${postId}")
            }
            headers{
                headers.append("Authorization", "Bearer ${BuildConfig.SUPERBASE_KEY}")
            }
        }.body<List<Post>>()[0] //List로 받아서 0번째를 반환한다.
    }

    //포스트 삭제하기
    suspend fun deletePostItem(postId: String) : HttpResponse { //postId를 받아서 Post를 반환한다. 1개만 반환한다해서 List대신 단일 Post를 사용

        return KtorClient.httpClient.delete(postUrl){
            url {
                parameters.append("id", "eq.${postId}")
            }
            headers{
                headers.append("Authorization", "Bearer ${BuildConfig.SUPERBASE_KEY}")
            }
        }
    }

    //단일 포스트 추가하기
    suspend fun addPostItem(
        title: String,
        content: String? = null,
    ) : HttpResponse { //postId를 받아서 Post를 반환한다. 1개만 반환한다해서 List대신 단일 Post를 사용

        return KtorClient.httpClient.post(postUrl){
            headers{
                headers.append("Authorization", "Bearer ${BuildConfig.SUPERBASE_KEY}")
            }
            setBody(PostRequest(title, content, UserInfo.userId))
        }
    }

    //단일 포스트 수정하기
    suspend fun editPostItem(
        postId: String,
        title: String,
        content: String? = null
    ) : HttpResponse { //postId를 받아서 Post를 반환한다. 1개만 반환한다해서 List대신 단일 Post를 사용

        return KtorClient.httpClient.patch(postUrl){
            url {
                parameters.append("id", "eq.${postId}")
            }
            headers{
                headers.append("Authorization", "Bearer ${BuildConfig.SUPERBASE_KEY}")
            }
            setBody(PostRequest(title, content))
        }
    }


}