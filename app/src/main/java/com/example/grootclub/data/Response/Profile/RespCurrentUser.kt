package com.example.grootclub.data.Response.Profile

data class RespCurrentUser(
    val __v: Int,
    val _id: String,
    val age: String,
    val birthday: String,
    val email: String,
    val fname: String,
    val gender: String,
    val img: String,
    val isActive: Boolean,
    val lname: String,
    val phone: String,
    val role: String,
    val username: String
)

data class RespUpdateUser(
    val _id: String,
    val username: String,
    val password: String,
    val role: String,
    val isActive: Boolean,
    val img: String,
    val fname: String,
    val lname: String,
    val gender: String,
    val birthday: String,
    val age: String,
    val email: String,
    val phone: String,
    val __v: Int
)