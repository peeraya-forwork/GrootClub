package com.example.grootclub.data.Response.SignIn

data class RespSignIn(
    val token: String?,
    val payload: Payload?
)

data class Payload(
    val user: User?
)

data class User(
    val userId: String?,
    val username: String?,
    val password: String?,
    val role: String?
)
