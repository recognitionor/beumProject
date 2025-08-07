package com.kal.beum.login.domain

interface LoginClient {
    fun login(type: Int, callback: (Result<String>) -> Unit)
    fun logout(type: Int)
}