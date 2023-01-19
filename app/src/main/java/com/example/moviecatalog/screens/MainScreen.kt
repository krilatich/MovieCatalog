package com.example.moviecatalog.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.moviecatalog.MainViewModel
import com.example.moviecatalog.NavigationBottomBar
import com.example.moviecatalog.R
import com.example.moviecatalog.RatingIcon
import com.example.moviecatalog.data.FavoritesResponseModel
import com.example.moviecatalog.data.Movie
import com.example.moviecatalog.data.Review
import com.example.moviecatalog.network.Network
import com.example.moviecatalog.ui.theme.MovieCatalogTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun MainScreen(navController: NavController) {



   val topPicture = remember { mutableStateOf("")}
    val firstMovieId =  remember { mutableStateOf("")}


    Scaffold(bottomBar = { NavigationBottomBar(navController = navController) }) {


        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {




            Box(
                Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            ) {


                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                    //.align(alignment = Alignment.TopCenter)
                    ,
                    contentScale = ContentScale.Crop,
                    painter = rememberAsyncImagePainter(topPicture.value),
                    contentDescription = "topPictureLabel"
                )


                Box(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(35.dp)
                ) {
                    Button(
                        onClick = {

                            navController.navigate("movie_screen/${firstMovieId.value}")
                        },
                        modifier = Modifier
                            .height(40.dp)
                            .width(130.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                        ),
                        shape = RoundedCornerShape(4.dp),
                        elevation = ButtonDefaults.elevation(0.dp),
                    ) {
                        Text(
                            "Смотреть",
                            color = MaterialTheme.colors.secondary,
                            style = MaterialTheme.typography.body2
                        )
                    }
                }


            }

            Spacer(Modifier.height(10.dp))



            val isUpdated = remember { mutableStateOf(false)}
            val favorites = remember { mutableStateOf(listOf<Movie>()) }
            val api = Network.getFavoriteApi()
            val callTargetResponse: Call<FavoritesResponseModel> =
                api.getFavorites("Bearer ${Network.token}")


            if(!isUpdated.value) {
                callTargetResponse.enqueue(object : Callback<FavoritesResponseModel> {

                    override fun onResponse(
                        call: Call<FavoritesResponseModel>,
                        response: Response<FavoritesResponseModel>
                    ) {
                        if(response.isSuccessful)
                        favorites.value = response.body()!!.movies

                        else navController.navigate("signIn_screen")

                    }

                    override fun onFailure(call: Call<FavoritesResponseModel>, t: Throwable) {
                        navController.navigate("signIn_screen")
                    }

                })

                isUpdated.value = true
            }



                if (favorites.value.isNotEmpty()) Text(
                    text = "Избранное",
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 20.dp)
                )

                FavoriteList(favorites.value,navController = navController,isUpdated)


            Column(
                Modifier.padding(start = 20.dp)

            ) {

                Text(
                    text = "Галерея",
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.align(Alignment.Start)
                )



                MovieList(
                    modifier = Modifier.height(500.dp),
                    navController = navController,
                    topPicture = topPicture,
                    firstMovieId = firstMovieId
                )


            }




        }
    }
}





@Composable
fun FavoriteMovie(movie: Movie,navController: NavController,onDelete: () -> Unit = {}) {
    Box(
        Modifier
            .height(160.dp)
            .width(100.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {

        Text(movie.id)
        Image(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = { navController.navigate("movie_screen/${movie.id}") }),

            contentScale = ContentScale.Crop,
            painter = rememberAsyncImagePainter(model = movie.poster),
            contentDescription = "favoriteMovieImage",
        )


        Image(painter = painterResource(id = R.drawable.delete_favorite),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(30.dp)
                .clickable(onClick = onDelete),
        contentDescription = "deleteFavorite")
        
    }
}



@Composable
fun Movie(movie: Movie, navController: NavController) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 10.dp)
        .clickable { navController.navigate("movie_screen/${movie.id}") }) {

        Image(
            modifier = Modifier
                .height(150.dp)
                .width(100.dp)

            ,
            contentScale = ContentScale.Crop,
            painter = rememberAsyncImagePainter(model = movie.poster),
            contentDescription = "movieImage"
        )

        Column(
            modifier = Modifier
                .padding(start = 10.dp)
                .height(150.dp),

        )
        {

            Text(
                text = movie.name,
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier

            )
            Row() {
                Text(
                    text = movie.year.toString(),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier

                )

                Text(
                    text = " • ",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier

                )

                Text(
                    text = movie.country,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier

                )
            }

            var genres = movie.genres.first().name

            for (genre in movie.genres) {
                    if(genre!=movie.genres.first())
                  genres = "$genres, ${genre.name}"
                }

            Text(
                text = genres,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier

            )


            Spacer(Modifier.weight(1f))
            RatingIcon(rating = calculateRating(movie.reviews))


        }
    }

}

fun calculateRating(ratings:List<Review>):Double{

    var avg = 0f

   for(r in ratings) avg+=r.rating

    return (((avg/ratings.size*10).toInt()).toDouble()/10)

}



@Composable
private fun FavoriteList(favoritesList: List<Movie>,navController: NavController,isUpdated: MutableState<Boolean>) {
    LazyRow (horizontalArrangement = Arrangement.spacedBy(10.dp)){
        items(favoritesList) { favorite: Movie ->
            FavoriteMovie(movie = favorite,navController, onDelete = {
                val api = Network.getFavoriteApi()
                val callTargetResponse: Call<Unit> =
                    api.deleteFavorites(
                        token = "Bearer ${Network.token}",
                        id = favorite.id,
                        )


                callTargetResponse.enqueue(object : Callback<Unit> {

                    override fun onResponse(
                        call: Call<Unit>, response: Response<Unit>
                    ) {
                        if(response.isSuccessful)
                        isUpdated.value = false
                        else
                            navController.navigate("signIn_screen")
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        navController.navigate("signIn_screen")
                    }

                })
            })
        }
    }

}


@Composable
private fun MovieList(
    modifier: Modifier = Modifier,
    navController: NavController,
    topPicture:MutableState<String>,
    firstMovieId:MutableState<String>
) {
    val viewModel = viewModel<MainViewModel>()
    val state = viewModel.state
    LazyColumn(modifier) {
        items(state.items.size) { i ->
            val item = state.items[i]
            if(i==0) {
                topPicture.value = state.items[i].poster
                firstMovieId.value = state.items[i].id
            }
            else {
                if (i >= state.items.size - 1 && !state.endReached && !state.isLoading) {
                    viewModel.loadNextItems()
                }
                Movie(movie = item, navController = navController)
            }
        }
        item{
            if(state.isLoading){
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
                    ){
                    CircularProgressIndicator()
                }
            }
        }
    }

}


@Composable
@Preview
fun Preview2() {
    MovieCatalogTheme {
        MainScreen(navController = rememberNavController())
    }

}