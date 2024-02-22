package com.example.grootclub.data.Remote.Repository.SignIn

import com.example.grootclub.data.Remote.Service
import com.example.grootclub.data.Request.SignIn.ReqSignIn
import com.example.grootclub.data.Response.SignIn.RespSignIn
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class SignInRepository(private val service: Service) {
    suspend fun postSignIn(reqSignIn: ReqSignIn): Response<RespSignIn> {
        try {
            val response = service.signIn(reqSignIn)
            return if (response.isSuccessful) {
                Response.success(response.body())
            } else {
                val errorBody = response.errorBody()?.string()
                return Response.error(response.code(), errorBody?.toResponseBody())
            }
        } catch (e: Exception) {
            return Response.error(
                500,
                e.message?.toResponseBody() ?: "Unknown error".toResponseBody()
            )
        }
    }
}










