package com.example.moviecatalog.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moviecatalog.NavigationBottomBar
import com.example.moviecatalog.R
import com.example.moviecatalog.RatingIcon
import com.example.moviecatalog.data.DataSource
import com.example.moviecatalog.data.Favorite
import com.example.moviecatalog.data.Movie
import com.example.moviecatalog.ui.theme.MovieCatalogTheme


@Composable
fun MainScreen(navController: NavController) {
    Scaffold(bottomBar = {NavigationBottomBar(navController = navController)}) {

        Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize(1f)
            .verticalScroll(rememberScrollState())
            ,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    )
    {

        Box(
            Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                //.align(alignment = Alignment.TopCenter)
                ,
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.top_picture),
                contentDescription = "topPictureLabel"
            )
            Box(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(35.dp)
            ) {
                Button(
                    onClick = {
                        //navController.navigate("")
                    }, modifier = Modifier
                        .height(40.dp)
                        .width(130.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                    ),
                    shape = RoundedCornerShape(4.dp),
                    elevation = ButtonDefaults.elevation(0.dp),
                )
                {
                    Text(
                        "Смотреть",
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.body2
                    )
                }
            }


        }

        Spacer(Modifier.height(10.dp))


        Text(
            text = "Избранное",
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 20.dp)
        )


        FavoriteList(favoritesList = DataSource().favoritesList())


        Column(
            Modifier
                .padding(start = 20.dp)

        ) {

            Text(
                text = "Галерея",
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .align(Alignment.Start)
            )

            MovieList(
                movieList = DataSource().movieList(),
                modifier = Modifier.height(500.dp),
                navController = navController)
        }

        //Spacer(Modifier.weight(1f))
        // NavigationBottomBar(navController = navController)


    }
        }
    }




@Composable
fun FavoriteMovie(movie:Favorite){
    Box(
        Modifier
            .height(160.dp)
            .width(100.dp)
    ){
        Image(
            modifier = Modifier
                .fillMaxSize()
            //.align(alignment = Alignment.TopCenter)
            ,
            contentScale = ContentScale.Crop,
            painter = painterResource(movie.poster),
            contentDescription = "favoriteMovieImage"
        )
        IconButton(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = {  }) {
            Icon(Icons.Outlined.Close, contentDescription = "Удалить из избранных")
        }
    }
}


@Composable
fun Movie(movie:Movie,navController: NavController){

    Row(modifier = Modifier.fillMaxWidth()
        .clickable{navController.navigate("movie_screen")}){

        Image(
            modifier = Modifier
                .height(160.dp)
                .width(100.dp)
            //.align(alignment = Alignment.TopCenter)
            ,
            contentScale = ContentScale.Crop,
            painter = painterResource(movie.poster),
            contentDescription = "movieImage"
        )

        Column(modifier = Modifier
            .padding(10.dp))
        {

            Text(
                text = movie.name,
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier

            )
            Row(){
                Text(
                    text = movie.year.toString(),
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

            Row(){
                for(genre in movie.genres) Text(
                    text = genre,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier

                )
            }

            Spacer(Modifier.height(50.dp))

            RatingIcon(rating = 10.0)




        }
    }

}


@Composable
private fun FavoriteList(favoritesList: List<Favorite>, modifier: Modifier = Modifier){
    LazyRow(){
    items(favoritesList){
    favorite: Favorite ->
    FavoriteMovie(movie = favorite)
    }
}

}

@Composable
private fun MovieList(movieList: List<Movie>, modifier: Modifier = Modifier,navController: NavController){
    LazyColumn(modifier){
        items(movieList){
                movie: Movie ->
            Movie(movie = movie, navController = navController)
        }
    }

}


@Composable
@Preview
fun Preview2(){
    MovieCatalogTheme {
        MainScreen(navController = rememberNavController())
    }

}