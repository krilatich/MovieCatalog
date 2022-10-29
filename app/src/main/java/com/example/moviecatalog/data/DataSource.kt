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

        fun movieList(): List<Movie> {
            return listOf<Movie>(
                Movie(
                    poster = R.drawable.favorite_image,
                    name = "NAME", year = 1999,
                    country = "COUNTRY",
                    listOf("GENRE1", "GENRE2")
                ),
                Movie(
                    poster = R.drawable.favorite_image,
                    name = "NAME", year = 1999,
                    country = "COUNTRY",
                    listOf("GENRE1", "GENRE2")
                ),
                Movie(
                    poster = R.drawable.favorite_image,
                    name = "NAME", year = 1999,
                    country = "COUNTRY",
                    listOf("GENRE1", "GENRE2")
                ),
                Movie(
                    poster = R.drawable.favorite_image,
                    name = "NAME", year = 1999,
                    country = "COUNTRY",
                    listOf("GENRE1", "GENRE2")
                ),
                Movie(
                    poster = R.drawable.favorite_image,
                    name = "NAME", year = 1999,
                    country = "COUNTRY",
                    listOf("GENRE1", "GENRE2")
                ),
                Movie(
                    poster = R.drawable.favorite_image,
                    name = "NAME", year = 1999,
                    country = "COUNTRY",
                    listOf("GENRE1", "GENRE2")
                ),
            )
        }




}