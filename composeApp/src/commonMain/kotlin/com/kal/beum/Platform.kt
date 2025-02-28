package com.kal.beum

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform