package com.example.hackathon.login

import android.app.Application
import com.example.hackathon.BuildConfig
import com.kakao.sdk.common.KakaoSdk

class kakao : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}