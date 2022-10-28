package com.example.moviecatalog.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moviecatalog.R
import com.example.moviecatalog.ui.theme.MovieCatalogTheme
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        Image(
            modifier = Modifier
                .height(200.dp)
                .width(300.dp),
            painter = painterResource(id = R.drawable.mc_logo),
            contentDescription = stringResource(R.string.mc_logo)
        )

        Text(
            text = stringResource(R.string.MovieCatalog),
            style = TextStyle(fontSize = 50.sp, fontFamily =
            FontFamily(Font(R.font.ibmplex_bold))),
            color = MaterialTheme.colors.primary

        )

    }
    LaunchedEffect(key1 = true){
        delay(500L)
        navController.navigate("signIn_screen")
    }
}

@Preview
@Composable
fun Preview(){
    MovieCatalogTheme() {
        SplashScreen(navController = rememberNavController())
    }

}