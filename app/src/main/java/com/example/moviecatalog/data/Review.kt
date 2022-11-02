package com.example.moviecatalog.data

data class Review (
    val reviewText:String,
    val rating: Double,
    val isAnonymous: Boolean,
    val createDateTime: String,
    val author: Author
    )
