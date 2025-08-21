package com.kal.beum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kakao.sdk.common.KakaoSdk
import com.kal.beum.common.SocialKey
import com.navercorp.nid.NaverIdLoginSDK
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NaverIdLoginSDK.initialize(this, SocialKey.NAVER_CLIENT_ID, SocialKey.NAVER_CLIENT_SECRET, SocialKey.NAVER_CLIENT_NAME)
        initPlatformContext(this)

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}