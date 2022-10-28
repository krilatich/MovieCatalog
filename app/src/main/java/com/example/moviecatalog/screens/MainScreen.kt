package com.example.moviecatalog.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.moviecatalog.R
import com.example.moviecatalog.data.DataSource
import com.example.moviecatalog.data.Favorite
import com.example.moviecatalog.ui.theme.MovieCatalogTheme


@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize(1f),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    )
    {

        Box(
            Modifier
                .fillMaxWidth()
                .height(200.dp)){
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
                    .padding(35.dp)) {
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
                    elevation = ButtonDefaults.elevation(0.dp)
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



            Text(
                text = "Избранное",
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 20.dp)
            )


        FavoriteList(favoritesList = DataSource().favoritesList())


        Column(Modifier.padding(20.dp)) {





        }



        }
    }




@Composable
fun FavoriteMovie(movie:Favorite){
    Box(
        Modifier
            .height(180.dp)
            .width((120.dp))
    ){
        Image(
            modifier = Modifier
                .fillMaxSize()
            //.align(alignment = Alignment.TopCenter)
            ,
            contentScale = ContentScale.Crop,
            painter = painterResource(movie.image),
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
private fun FavoriteList(favoritesList: List<Favorite>, modifier: Modifier = Modifier){
    LazyRow(){
    items(favoritesList){
    favorite: Favorite ->
    FavoriteMovie(movie = favorite)
    }
}

}

@Composable
@Preview
fun Preview2(){
    MovieCatalogTheme {
        MainScreen()
    }

}