package com.kal.beum

import android.content.Context
import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

private lateinit var appContext: Context

fun initPlatformContext(context: Context) {
    appContext = context
}

actual fun getPlatform(): Platform = AndroidPlatform()
actual fun getPlatformContext(): Any? = appContext