package com.example.moviecatalog.data




@kotlinx.serialization.Serializable
data class Review(
    val id:String,
    val rating:Int
)