package com.example.grootclub.data.Remote

import com.example.grootclub.data.CoachListModel
import com.example.grootclub.data.Request.Profile.ReqUpdateProfile
import com.example.grootclub.data.Request.SignIn.ReqSignIn
import com.example.grootclub.data.Request.SignUp.ReqSignUp
import com.example.grootclub.data.Response.Profile.RespCurrentUser
import com.example.grootclub.data.Response.Profile.RespUpdateUser
import com.example.grootclub.data.Response.SignIn.RespSignIn
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface Service {
    @POST("/users/login")
    suspend fun signIn(@Body reqSignIn: ReqSignIn): Response<RespSignIn>

    @POST("/users/register")
    suspend fun signUp(@Body reqSignUp: ReqSignUp): Response<ResponseBody>

    @GET("/coachList/all")
    suspend fun getAllCoach(): CoachListModel


    @POST("/users/current-user")
    suspend fun currentUser(): Response<RespCurrentUser>

    @PUT("/users/updateuser/65b12e7b9ee95b2558e0b4fc")
    suspend fun updateUser(@Body reqUpdateProfile: ReqUpdateProfile): Response<RespUpdateUser>
}
