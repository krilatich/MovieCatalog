package com.example.moviecatalog.network

import com.example.moviecatalog.data.FavoritesResponseModel
import retrofit2.Call
import retrofit2.http.*

interface FavoriteApi {


    @GET("api/favorites")
    fun getFavorites(@Header("Authorization") token:String): Call<FavoritesResponseModel>

    @POST("api/favorites/{id}/add")
    fun addFavorites(@Header("Authorization") token:String,@Path("id") id:String): Call<Unit>

    @DELETE("api/favorites/{id}/delete")
    fun deleteFavorites(@Header("Authorization") token:String,@Path("id") id:String): Call<Unit>
}