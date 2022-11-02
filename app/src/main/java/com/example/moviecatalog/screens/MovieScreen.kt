package com.example.moviecatalog.screens


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moviecatalog.R
import com.example.moviecatalog.RatingIcon
import com.example.moviecatalog.data.DataSource
import com.example.moviecatalog.data.Review
import com.example.moviecatalog.ui.theme.MovieCatalogTheme
import com.example.moviecatalog.ui.theme.White200
import com.example.moviecatalog.ui.theme.detailColor
import java.text.NumberFormat
import java.util.*


@Composable
fun MovieScreen(navController: NavController) {


    val format = NumberFormat.getCurrencyInstance()
    format.currency = Currency.getInstance("USD")

    val time: Int = 0
    val tagLine: String = "«Страх - это кандалы. Надежда - это свобода»"
    val director: String = "Фрэнк Дарабонт"
    val budget: Int = 25000000
    val fees: Int = 28418687
    val ageLimit: Int = 16
    val name: String = "Побег из Шоушенка"
    val year: Int = 2000
    val country: String = "США"
    val genres = listOf("фантастика", "боевик", "мелодрама", "драма", "мюзикл")
    val description = "Бухгалтер Энди Дюфрейн обвинён в убийстве собственной жены " +
            "и её любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается " +
            "с жестокостью и беззаконием, царящими по обе стороны решётки. Каждый, кто попадает " +
            "в эти стены, становится их рабом до конца жизни. Но Энди, обладающий живым умом и доброй" +
            " душой, находит подход как к заключённым, так и к охранникам, добиваясь их особого к себе " +
            "расположения"


    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState()),

        ) {


        Row(
            Modifier.padding(20.dp)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.h1,
                color = Color.White,
                modifier = Modifier.padding(start = 50.dp)
            )
            Spacer(Modifier.width(25.dp))
            Icon(
                imageVector = Icons.Outlined.Favorite,
                contentDescription = "favorite",
                Modifier
                    .clickable(onClick = {})
                    .padding(top = 5.dp),
                tint = MaterialTheme.colors.primary
            )
        }

        Box() {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)

                //.align(alignment = Alignment.TopCenter)
                ,
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.top_picture),
                contentDescription = "topPictureLabel"
            )
            Text(
                text = name,
                style = MaterialTheme.typography.caption,
                color = Color.White,
                modifier = Modifier
                    .width(150.dp)
                    .align(Alignment.BottomStart)
                    .padding(start = 20.dp)
            )

        }




        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize(1f)
                .padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Text(
                text = description,
                style = MaterialTheme.typography.body1,
                color = Color.White,
            )
            Text(
                text = "О фильме",
                style = MaterialTheme.typography.body2,
                color = Color.White,
            )

            Column() {
                DetailRow(name = "Год", value = year.toString())
                DetailRow(name = "Страна", value = country)
                DetailRow(name = "Время", value = time.toString())
                DetailRow(name = "Слоган", value = tagLine)
                DetailRow(name = "Режиссер", value = director)
                DetailRow(name = "Бюджет", value = format.format(budget))
                DetailRow(name = "Сборы в мире", value = format.format(fees))
                DetailRow(name = "Возраст", value = "$ageLimit+")
            }

            Text(
                text = "Жанры",
                style = MaterialTheme.typography.body2,
                color = Color.White,
            )

            GenreList(genreList = genres, modifier = Modifier.height(60.dp))

            Row() {
                Text(
                    text = "Отзывы",
                    style = MaterialTheme.typography.body2,
                    color = Color.White,
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "addReview",
                    Modifier
                        .clickable(onClick = {}),
                    tint = MaterialTheme.colors.primary
                )
            }

            ReviewList(reviewList = DataSource().reviewList(), modifier = Modifier.height(500.dp))


        }


    }



    Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "favorite",
        Modifier
            .clickable(onClick = { navController.navigate("main_screen") })
            .padding(25.dp),
        tint = Color.White,
    )

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GenreList(genreList: List<String>, modifier: Modifier) {

    LazyVerticalGrid(cells = GridCells.Adaptive(75.dp), modifier = modifier)
    {
        items(genreList) { item ->
            Surface(
                shape = RoundedCornerShape(5.dp),
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(end = 5.dp, bottom = 5.dp)
                    .height(25.dp),
            ) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.body1,
                    color = White200,
                    modifier = Modifier.padding(5.dp),

                    )
            }

        }

    }


}

@Composable
fun ReviewList(reviewList: List<Review>, modifier: Modifier) {

    LazyColumn(verticalArrangement = Arrangement.spacedBy(5.dp), modifier = modifier) {
        items(reviewList) { review: Review ->
            Review(review = review)
        }

    }
}

@Composable
fun Review(review: Review, isMy: Boolean = true) {

    Card( border = BorderStroke
        (1.dp, MaterialTheme.colors.secondary),
        backgroundColor = MaterialTheme.colors.background,
        ) {
        Column(    modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {


            Row(verticalAlignment = Alignment.CenterVertically) {
                if (review.isAnonymous) {

                    Image(
                        painter = painterResource(id = R.drawable.profile_image),
                        contentDescription = "avatar"
                    )
                    Column() {
                        Text(
                            text = "Анонимный пользователь",
                            style = MaterialTheme.typography.body2,
                            color = Color.White,
                        )
                    }
                } else {

                    Image(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(id = review.author.avatar),
                        contentDescription = "avatar"
                    )
                    Column(Modifier.padding(start = 10.dp)) {
                        Text(
                            text = review.author.nickName,
                            style = MaterialTheme.typography.body2,
                            color = Color.White,
                        )
                        if (isMy)
                            Text(
                                text = "мой отзыв",
                                style = MaterialTheme.typography.body1,
                                color = detailColor,
                            )
                    }
                }


                Spacer(modifier = Modifier.weight(1f))
                RatingIcon(rating = review.rating)

            }



            Text(
                text = review.reviewText,
                style = MaterialTheme.typography.body1,
                color = Color.White,
            )
            Row() {
                Text(
                    text = review.createDateTime,
                    style = MaterialTheme.typography.body1,
                    color = Color.White,
                )
                if (isMy){
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "editReview",
                        Modifier
                            .clickable(onClick = {}),
                        tint = Color.Gray
                    )
                    Spacer(Modifier.width(5.dp))
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "deleteReview",
                        Modifier
                            .clickable(onClick = {}),
                        tint = Color.Red
                    )

                }


            }
        }
    }
}



@Composable
fun DetailRow(name: String, value: String) {
    Row() {
        Text(
            text = name,
            style = MaterialTheme.typography.body1,
            color = detailColor,
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body1,
            color = Color.White
        )

    }
}

@Composable
@Preview
fun Pre() {
    MovieCatalogTheme {
        MovieScreen(navController = rememberNavController())
    }
}