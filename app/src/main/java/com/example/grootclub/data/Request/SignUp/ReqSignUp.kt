package com.example.grootclub.data.Request.SignUp

data class ReqSignUp(
    val age: Int,
    val birthday: String,
    val email: String,
    val fname: String,
    val gender: String,
    val img: String,
    val lname: String,
    val password: String,
    val phone: String,
    val username: String
)