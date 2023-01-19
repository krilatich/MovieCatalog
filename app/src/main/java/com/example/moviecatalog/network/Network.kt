package com.example.moviecatalog.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object Network {
    private const val BASE_URL = "https://react-midterm.kreosoft.space/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }





    private fun getRetrofit(): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory( json.asConverterFactory("application/json".toMediaType()) )
            .build()




        return retrofit
    }
    var token: String? = null

    var userId: String? = null

    var userNickname: String? = null

    private val retrofit = getRetrofit()

    fun getAuthApi():AuthApi = retrofit.create(AuthApi::class.java)

    fun getFavoriteApi():FavoriteApi = retrofit.create(FavoriteApi::class.java)

    fun getMovieApi():MovieApi = retrofit.create(MovieApi::class.java)

    fun getUserApi():UserApi = retrofit.create(UserApi::class.java)

    fun getReviewApi():ReviewApi = retrofit.create(ReviewApi::class.java)


}