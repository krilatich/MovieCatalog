package com.example.moviecatalog.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("api/account/register")
    fun register(@Body body: RegisterRequestBody): Call<TokenResponse>


    @POST("api/account/login")
    fun login(@Body body: LoginRequestBody): Call<TokenResponse>

    @POST("api/account/logout")
    fun logout(@Header("Authorization") token:String): Call<Unit>
}