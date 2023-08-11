package com.example.hackathon.network

object UserInfo { // 임시로 로그인하면 저장을 함
    var accessToken: String = ""
    var userId : String = ""
    var userEmail: String = ""

    fun clearData(){
        this.accessToken = ""
        this.userId = ""
        this.userEmail = ""
    }
}