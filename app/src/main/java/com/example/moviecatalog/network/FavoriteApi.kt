package com.example.moviecatalog.network

import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteApi {

    @POST("api/favorites/{id}/add")
    suspend fun postFavorites(@Path("id") id: String){}

}