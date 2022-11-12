package com.example.moviecatalog.network

@kotlinx.serialization.Serializable
data class TokenResponse(
    val accessToken:String,
    //val refreshToken: TokenResponse,
)