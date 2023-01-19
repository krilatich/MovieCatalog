package com.example.moviecatalog.data

/*
data class Movie(
    @DrawableRes val poster:Int,
    val name:String,
    val year:Int,
    val country:String,
    val genres:List<String>
    )

*/
@kotlinx.serialization.Serializable
data class Movie(
    val id: String,
    val poster:String,
    val name:String,
    val year:Int,
    val country:String,
    val genres:List<Genre>,
    val reviews:List<Review>
)

