package com.example.grootclub.data.Request.Profile
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class ReqUpdateProfile(
    @SerialName("age")
    val age: String? = null,
    @SerialName("birthday")
    val birthDay: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("fname")
    val fName: String? = null,
    @SerialName("gender")
    val gender: String? = null,
    @SerialName("_id")
    val id: String? = null,
    @SerialName("img")
    val img: String? = null,
    @SerialName("lname")
    val lName: String? = null,
    @SerialName("phone")
    val phone: String? = null
)