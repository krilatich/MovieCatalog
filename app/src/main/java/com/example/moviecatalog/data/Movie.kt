package com.example.moviecatalog.data

import androidx.annotation.DrawableRes

data class Movie(
    @DrawableRes val poster:Int,
    val name:String,
    val year:Int,
    val country:String,
    val genres:List<String>
    )


