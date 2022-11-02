package com.example.moviecatalog.data

import androidx.annotation.DrawableRes

data class Author
    (
val userId: String,
val nickName: String,
@DrawableRes val avatar: Int
)
