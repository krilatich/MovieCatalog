package com.example.moviecatalog.data




@kotlinx.serialization.Serializable
data class Author
    (
val userId: String,
val nickName: String,
val avatar: String?
)
