package com.example.moviecatalog.screens


import android.util.Log
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.moviecatalog.R
import com.example.moviecatalog.RatingIcon
import com.example.moviecatalog.data.*
import com.example.moviecatalog.network.Network
import com.example.moviecatalog.ui.theme.White200
import com.example.moviecatalog.ui.theme.detailColor
import com.example.moviecatalog.ui.theme.dialogColor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.math.min


@Composable
fun MovieScreen(navController: NavController, movieId:String) {

    var isFavorite by remember {
        mutableStateOf(false)
    }
    val api = Network.getFavoriteApi()
    val callTargetResponse: Call<FavoritesResponseModel> =
        api.getFavorites("Bearer ${Network.token}")

    callTargetResponse.enqueue(object : Callback<FavoritesResponseModel> {

        override fun onResponse(
            call: Call<FavoritesResponseModel>,
            response: Response<FavoritesResponseModel>
        ) {
            if(response.isSuccessful){
            for(i in response.body()!!.movies)
                if(i.id == movieId) isFavorite = true}
            else navController.navigate("signIn_screen")

        }

        override fun onFailure(call: Call<FavoritesResponseModel>, t: Throwable) {
            navController.navigate("signIn_screen")
        }

    })






    val isUpdated = remember { mutableStateOf(false) }




    var movie by remember  {mutableStateOf<MovieDetailsResponseModel?>(
        MovieDetailsResponseModel(
            id = "b6c5228b-91fb-43a1-a2ac-08d9b9f3d2a2",
            ageLimit = 18,
            poster = "string",
            description = "string",
            fees = 2000,
            genres = listOf(Genre("string","string")),
            reviews = listOf(
                ReviewDetails(id="String",
            reviewText = "String",
                    rating = 34,
            isAnonymous = false,
            createDateTime = "sssasa1",
            author = Author(userId = "string", nickName = "string", avatar = "string"))
            ),
            budget = 188,
            time = 10,
            name = "string",
            tagline = "sting",
            year = 19,
            director = "string",
            country = "string"
            )

    )}


    if(!isUpdated.value) {

        val api = Network.getMovieApi()
        val callTargetResponse: Call<MovieDetailsResponseModel> =
            api.getMovie(movieId)


        callTargetResponse.enqueue(object : Callback<MovieDetailsResponseModel> {

            override fun onResponse(
                call: Call<MovieDetailsResponseModel>, response: Response<MovieDetailsResponseModel>
            ) {
                if (response.isSuccessful)
                    movie = response.body()!!

                Log.d("responseDETAIL", response.body()!!.toString())


            }

            override fun onFailure(call: Call<MovieDetailsResponseModel>, t: Throwable) {
                throw t
            }

        })

        isUpdated.value = true

    }
    val format = NumberFormat.getCurrencyInstance()
    format.currency = Currency.getInstance("USD")



    val dialogIsOpen  = remember {mutableStateOf(false)}

    var myReview:ReviewDetails? = null


    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .verticalScroll(scrollState),

        ) {


        Box(
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .graphicsLayer {
                    alpha = 1f - (scrollState.value.toFloat() / scrollState.maxValue)
                    translationY = 0.5f * scrollState.value
                }) {
            Image(
                modifier = Modifier
                    .fillMaxSize()


                ,
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(model = movie!!.poster),
                contentDescription = "topPictureLabel"
            )
            Text(
                text = movie!!.name,
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
                text = movie!!.description,
                style = MaterialTheme.typography.body1,
                color = Color.White,
            )
            Text(
                text = "О фильме",
                style = MaterialTheme.typography.body2,
                color = Color.White,
            )

            Column() {
                DetailRow(name = "Год", value = movie!!.year.toString())
                DetailRow(name = "Страна", value = movie!!.country)
                DetailRow(name = "Время", value = movie!!.time.toString())
                DetailRow(name = "Слоган", value = movie!!.tagline)
                DetailRow(name = "Режиссер", value = movie!!.director)
                DetailRow(name = "Бюджет",
                    value = format.format(movie!!.budget)
                    )
                DetailRow(name = "Сборы в мире",
                    value = format.format(movie!!.fees)
                  )
                DetailRow(name = "Возраст", value = "${movie!!.ageLimit}+")
            }

            Text(
                text = "Жанры",
                style = MaterialTheme.typography.body2,
                color = Color.White,
            )

            GenreList(genreList = movie!!.genres, modifier = Modifier.height(60.dp))

            Row() {
                Text(
                    text = "Отзывы",
                    style = MaterialTheme.typography.body2,
                    color = Color.White,
                )
                Spacer(Modifier.weight(1f))
                if(myReview==null) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "addReview",
                        Modifier
                            .clickable(onClick = { dialogIsOpen.value = true }),
                        tint = MaterialTheme.colors.primary,

                        )
                }
            }

            val reviewList:MutableList<ReviewDetails> = movie!!.reviews.toMutableList()


                for (r in reviewList) {

                    if (r.author.nickName == Network.userNickname!!.filter { !it.isWhitespace() }) {
                        myReview = r
                        reviewList.remove(r)
                    }
                }


           if(myReview!=null) {
               Review(myReview!!,
                   isMy = true,
                   onEdit = { dialogIsOpen.value = true },
                   onDelete = {
                       val api = Network.getReviewApi()
                       val callTargetResponse: Call<Unit> =
                           api.delete(
                               token = "Bearer ${Network.token}",
                               movieId = movieId,
                               id = myReview!!.id
                           )

                       callTargetResponse.enqueue(object : Callback<Unit> {

                           override fun onResponse(
                               call: Call<Unit>, response: Response<Unit>
                           ) {
                               if(response.isSuccessful){
                               isUpdated.value = false
                               dialogIsOpen.value = false}
                               else
                                   navController.navigate("signIn_screen")
                           }

                           override fun onFailure(call: Call<Unit>, t: Throwable) {
                               navController.navigate("signIn_screen")
                           }

                       })



                   })
           }

            ReviewList(reviewList = reviewList, modifier = Modifier.height(500.dp))


        }


    }

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                    .graphicsLayer {
                        alpha = min(1f, (scrollState.value.toFloat() / scrollState.maxValue) * 2)
                    }) {

                Row(
                    Modifier
                        .background(MaterialTheme.colors.background)
                        .fillMaxWidth()) {
                    movie?.let {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.h1,
                            color = Color.White,
                            modifier = Modifier.padding(start = 60.dp).width(230.dp)
                        )
                    }
                    Spacer(Modifier.width(25.dp))
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = "favorite",
                        Modifier
                            .clickable(onClick = {
                                if (isFavorite) {
                                    val api = Network.getFavoriteApi()
                                    val callTargetResponse: Call<Unit> =
                                        api.deleteFavorites(
                                            token = "Bearer ${Network.token}",
                                            id = movieId,
                                        )


                                    callTargetResponse.enqueue(object : Callback<Unit> {

                                        override fun onResponse(
                                            call: Call<Unit>, response: Response<Unit>
                                        ) {
                                            if(response.isSuccessful)
                                            isFavorite = !isFavorite

                                            else navController.navigate("signIn_screen")

                                        }

                                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                                            navController.navigate("signIn_screen")
                                        }

                                    })
                                } else {

                                    val api = Network.getFavoriteApi()
                                    val callTargetResponse: Call<Unit> =
                                        api.addFavorites(
                                            token = "Bearer ${Network.token}",
                                            id = movieId,
                                        )


                                    callTargetResponse.enqueue(object : Callback<Unit> {

                                        override fun onResponse(
                                            call: Call<Unit>, response: Response<Unit>
                                        ) {
                                            if(response.isSuccessful)
                                            isFavorite = !isFavorite

                                            else navController.navigate("signIn_screen")
                                        }

                                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                                            navController.navigate("signIn_screen")
                                        }

                                    })


                                }
                            })
                            .padding(top = 5.dp)
                            .background(MaterialTheme.colors.background),
                        tint = if (isFavorite) MaterialTheme.colors.primary
                        else MaterialTheme.colors.secondary
                    )
                }
            }

    Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "goBack",
        modifier = Modifier
            .clickable(onClick = { navController.navigate("main_screen") })
            .padding(top = 5.dp, start = 20.dp)

            ,
        tint = Color.White,
    )





     Dialog(movieId = movieId,dialogIsOpen= dialogIsOpen, myReview = myReview, isUpdated = isUpdated, navController = navController)



}
@Composable
fun Dialog(movieId:String ,myReview:ReviewDetails?, isUpdated:MutableState<Boolean>, dialogIsOpen:MutableState<Boolean>,
           navController: NavController){
    if(!dialogIsOpen.value) return

    var isEdit = false
    if(myReview!=null) isEdit = true

    var count by remember{ mutableStateOf(myReview?.rating?: 0)}
    var reviewText by remember { mutableStateOf(myReview?.reviewText?: "")}
    var isAnonymous by remember { mutableStateOf(myReview?.isAnonymous?: false)}

    Box(
        Modifier
            .fillMaxSize()
            .padding(20.dp)) {
        Column(
            modifier = Modifier

                .background(dialogColor)
                .align(Center)
                .padding(10.dp)

        ,
            verticalArrangement = Arrangement.spacedBy(10.dp),



        )
        {
            Text(
                text = "Оставить отзыв",
                style = MaterialTheme.typography.h1,
                color = Color.White,
            )
                Row(horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()){
                     for(i in 1..10) {

                         if (count >= i)
                             Image(

                                 painter = painterResource(id = R.drawable.orange_star),
                                 contentDescription = "$i",
                                 modifier = Modifier
                                     .size(20.dp)
                                     .clickable(onClick = {
                                         count = i
                                     })
                             )
                         else Image(
                             painter = painterResource(id = R.drawable.star_icon),
                             contentDescription = "$i",
                             modifier = Modifier
                                 .size(20.dp)
                                 .clickable(onClick = {
                                     count = i
                                 })
                         )


                     }
                }

            OutlinedTextField(reviewText, onValueChange = {reviewText = it},
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()


            )

            Row(verticalAlignment = CenterVertically){
                Text(
                    text = "Анонимный отзыв",
                    style = MaterialTheme.typography.body2,
                    color = Color.White,
                )
                Spacer(Modifier.weight(1f))
                Checkbox(
                    checked = isAnonymous,
                    onCheckedChange = { isAnonymous = it },

                )

            }

            Button(onClick = {

                if(!isEdit) {

                    val api = Network.getReviewApi()
                    val callTargetResponse: Call<Unit> =
                        api.add(
                            token = "Bearer ${Network.token}",
                            movieId = movieId,
                            body = ReviewRequestBody(
                                reviewText = reviewText,
                                rating = count,
                                isAnonymous = isAnonymous
                            )
                        )


                    callTargetResponse.enqueue(object : Callback<Unit> {

                        override fun onResponse(
                            call: Call<Unit>, response: Response<Unit>
                        ) {
                            if(response.isSuccessful){
                            isUpdated.value = false
                            dialogIsOpen.value = false}
                            else navController.navigate("signIn_screen")

                        }

                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                            navController.navigate("signIn_screen")
                        }

                    })
                }
                else{

                    val api = Network.getReviewApi()
                    val callTargetResponse: Call<Unit> =
                        api.edit(
                            token = "Bearer ${Network.token}",
                            movieId = movieId,
                            id = myReview!!.id,
                            body = ReviewRequestBody(
                                reviewText = reviewText,
                                rating = count,
                                isAnonymous = isAnonymous
                            )
                        )


                    callTargetResponse.enqueue(object : Callback<Unit> {

                        override fun onResponse(
                            call: Call<Unit>, response: Response<Unit>
                        ) {
                            if(response.isSuccessful){
                            isUpdated.value = false
                            dialogIsOpen.value = false}
                            else navController.navigate("signIn_screen")

                        }

                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                            navController.navigate("signIn_screen")
                        }

                    })


                }



                             },
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(40.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary
                    ),
                shape = RoundedCornerShape(4.dp)
            )
            {
                Text(
                    text = "Сохранить",
                    color = Color.White,
                    style = MaterialTheme.typography.body2
                )

            }

            Button(onClick = {
                dialogIsOpen.value = false
                             },modifier = Modifier
                .fillMaxWidth(1f)
                .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = dialogColor
                ),
                border =  null,
                shape = RoundedCornerShape(4.dp),
                elevation = ButtonDefaults.elevation(0.dp)
            )
            {
                Text(
                    text = "Отмена",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.body2
                )
            }


        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GenreList(genreList: List<Genre>, modifier: Modifier) {

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
                    text = item.name,
                    style = MaterialTheme.typography.body1,
                    color = White200,
                    modifier = Modifier.padding(5.dp),

                    )
            }

        }

    }


}

@Composable
fun ReviewList(reviewList: List<ReviewDetails>, modifier: Modifier) {

    LazyColumn(verticalArrangement = Arrangement.spacedBy(5.dp), modifier = modifier) {
        items(reviewList) { review: ReviewDetails ->
            Review(review = review)
        }

    }
}

@Composable
fun Review(review: ReviewDetails, isMy: Boolean = false, onEdit: () -> Unit = {},onDelete: () -> Unit = {}) {

    Card( border = BorderStroke
        (1.dp, MaterialTheme.colors.secondary),
        backgroundColor = MaterialTheme.colors.background,
        ) {
        Column(    modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {


            Row(verticalAlignment = Alignment.CenterVertically) {
                if (review.isAnonymous and !isMy) {

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
                        painter = if(review.author.avatar!=null) rememberAsyncImagePainter(model = review.author.avatar)
                        else painterResource(id = R.drawable.profile_image),
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
                RatingIcon(rating = review.rating.toDouble(),
                    isReview = true,
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp)
                )

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
                            .clickable(onClick = onEdit),
                        tint = Color.Gray
                    )
                    Spacer(Modifier.width(5.dp))
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "deleteReview",
                        Modifier
                            .clickable(onClick = onDelete),
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



