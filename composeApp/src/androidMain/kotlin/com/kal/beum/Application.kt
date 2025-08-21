package com.kal.beum

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kal.beum.di.initKoin
import org.koin.android.ext.koin.androidContext

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "d160e887f28e5ecc93c0ee9d631f4267")
        initKoin {
            androidContext(this@Application)
        }
    }
}