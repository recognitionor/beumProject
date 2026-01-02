package com.kal.beum.login.data.client

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.kal.beum.core.domain.DataError
import com.kal.beum.login.domain.LoginClient
import com.kal.beum.login.domain.SocialToken

actual class AppleLoginClient actual constructor(private val obj: Any?) : LoginClient {
    
    private val auth = FirebaseAuth.getInstance()
    
    actual override fun login(
        type: Int,
        callback: (SocialToken?, DataError.Remote?) -> Unit
    ) {
        val activity = obj as? Activity
        if (activity == null) {
            Log.e("AppleLoginClient", "Activity is null")
            callback.invoke(null, DataError.Remote.LOGIN_FAILED)
            return
        }
        
        val provider = OAuthProvider.newBuilder("apple.com")
            .setScopes(listOf("email", "name"))
            .build()
        
        // 이미 진행중인 인증이 있는지 확인
        val pending = auth.pendingAuthResult
        if (pending != null) {
            pending.addOnSuccessListener { authResult ->
                handleAuthResult(authResult.user, callback)
            }.addOnFailureListener { e ->
                Log.e("AppleLoginClient", "Apple login failed (pending): ${e.message}")
                callback.invoke(null, DataError.Remote.LOGIN_FAILED)
            }
        } else {
            // 새로운 Apple 로그인 플로우 시작 (Activity 기반 OAuth 웹뷰)
            auth.startActivityForSignInWithProvider(activity, provider)
                .addOnSuccessListener { authResult ->
                    handleAuthResult(authResult.user, callback)
                }
                .addOnFailureListener { e ->
                    Log.e("AppleLoginClient", "Apple login failed: ${e.message}")
                    callback.invoke(null, DataError.Remote.LOGIN_FAILED)
                }
        }
    }
    
    private fun handleAuthResult(
        user: com.google.firebase.auth.FirebaseUser?,
        callback: (SocialToken?, DataError.Remote?) -> Unit
    ) {
        if (user == null) {
            callback.invoke(null, DataError.Remote.LOGIN_FAILED)
            return
        }
        
        user.getIdToken(false).addOnSuccessListener { tokenResult ->
            val idToken = tokenResult.token ?: ""
            val uid = user.uid
            Log.d("AppleLoginClient", "Apple login success - uid: $uid")
            Log.d("AppleLoginClient", "Apple login success - idToken: $idToken")
            callback.invoke(SocialToken(idToken, uid), null)
        }.addOnFailureListener { e ->
            Log.e("AppleLoginClient", "Failed to get token: ${e.message}")
            callback.invoke(null, DataError.Remote.LOGIN_FAILED)
        }
    }

    actual override fun logout() {
        auth.signOut()
        Log.d("AppleLoginClient", "Apple logout success")
    }
}