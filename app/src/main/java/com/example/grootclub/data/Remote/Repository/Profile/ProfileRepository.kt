package com.example.grootclub.data.Remote.Repository.Profile

import com.example.grootclub.data.Remote.Service
import com.example.grootclub.data.Request.Profile.ReqUpdateProfile
import com.example.grootclub.data.Response.Profile.RespCurrentUser
import com.example.grootclub.data.Response.Profile.RespUpdateUser
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class ProfileRepository(private val service: Service) {
    suspend fun postCurrentUser(): Response<RespCurrentUser> {
        try {
            val response = service.currentUser()
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


    suspend fun updateUser(reqUpdateProfile: ReqUpdateProfile): Response<RespUpdateUser> {
        try {
            val response = service.updateUser(reqUpdateProfile)
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