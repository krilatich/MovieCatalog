package com.example.moviecatalog.data

import com.example.moviecatalog.R

class DataSource {

    fun favoritesList(): List<Favorite> {
            return listOf<Favorite>(
                Favorite(R.drawable.favorite_image),
                Favorite(R.drawable.favorite_image),
                Favorite(R.drawable.favorite_image),
                Favorite(R.drawable.favorite_image),
                Favorite(R.drawable.favorite_image),
                Favorite(R.drawable.favorite_image),
                Favorite(R.drawable.favorite_image),
                Favorite(R.drawable.favorite_image),
                Favorite(R.drawable.favorite_image),
                Favorite(R.drawable.favorite_image)
            )
    }



}