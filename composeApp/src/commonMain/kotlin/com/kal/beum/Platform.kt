package com.kal.beum

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getPlatformContext(): Any?