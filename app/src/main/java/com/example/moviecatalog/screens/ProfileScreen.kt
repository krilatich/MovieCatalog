package com.example.moviecatalog.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moviecatalog.*
import com.example.moviecatalog.R
import com.example.moviecatalog.data.ProfileResponseModel
import com.example.moviecatalog.network.Network
import com.example.moviecatalog.ui.theme.Black200
import com.example.moviecatalog.ui.theme.MovieCatalogTheme
import com.example.moviecatalog.ui.theme.White200
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ProfileScreen(navController: NavController) {

    var isUpdated by remember { mutableStateOf(false) }
    var isChanged by remember { mutableStateOf(false) }

    Scaffold(bottomBar = { NavigationBottomBar(navController = navController) }) {
        val focusManager = LocalFocusManager.current

        var avatarRefInput by remember { mutableStateOf("") }
        var emailInput by remember { mutableStateOf("") }
        var nameInput by remember { mutableStateOf("") }
        val birthDateInput = remember { mutableStateOf("") }
        var gender by remember { mutableStateOf(-1) }
        val openDialog = remember { mutableStateOf(false) }
        val errorList = mutableListOf<String>()

        val errorCode = remember { mutableStateOf<String?>(null) }



        if (errorCode.value != null) mToast(LocalContext.current, errorCode.value!!)

        var error = emailErrors(emailInput)
        if (error != null) {
            errorList.add(error)
        }

        error = nameErrors(nameInput)
        if (error != null) {
            errorList.add(error)
        }

        error = birthDateErrors(birthDateInput.value)
        if (error != null) {
            errorList.add(error)
        }

        error = genderErrors(gender)
        if (error != null) {
            errorList.add(error)
        }

        var oldProfile by remember { mutableStateOf<ProfileResponseModel?>(null) }

        if (!isUpdated) {

            val api = Network.getUserApi()
            val callTargetResponse: Call<ProfileResponseModel> =
                api.getProfile("Bearer ${Network.token}")


            callTargetResponse.enqueue(object : Callback<ProfileResponseModel> {

                override fun onResponse(
                    call: Call<ProfileResponseModel>, response: Response<ProfileResponseModel>
                ) {
                    if (response.isSuccessful) {
                        Network.userId = response.body()!!.id
                        oldProfile = response.body()!!
                        emailInput = response.body()!!.email
                        if (response.body()!!.avatarLink != null)
                            avatarRefInput = response.body()!!.avatarLink.toString()
                        birthDateInput.value = response.body()!!.birthDate
                        gender = response.body()!!.gender
                        nameInput = response.body()!!.name

                    } else navController.navigate("signIn_screen")

                }

                override fun onFailure(call: Call<ProfileResponseModel>, t: Throwable) {
                    navController.navigate("signIn_screen")
                }

            })


            isUpdated = true

        }
        oldProfile?.let {
            if (
                it.email != emailInput
                || it.avatarLink != avatarRefInput
                || it.name != nameInput
                || it.gender != gender
            )
                isChanged = true
        }



        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize(1f)
                .padding(20.dp),
            horizontalAlignment = Alignment.Start

        )
        {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                Image(
                    modifier = Modifier
                        .size(75.dp),
                    //contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.profile_image),
                    contentDescription = "profile_image"
                )
                Spacer(Modifier.width(20.dp))
                Text(
                    text = nameInput,
                    style = MaterialTheme.typography.h1,
                    color = White200
                )

            }
            Spacer(Modifier.height(20.dp))


            Text(
                text = "E_mail",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.secondary
            )
            EditField(
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                value = emailInput,
                onValueChanged = { emailInput = it },
            )
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "Ссылка на аватарку",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.secondary
            )
            EditField(
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                value = avatarRefInput,
                onValueChanged = { avatarRefInput = it },
            )
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "Имя",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.secondary
            )
            EditField(
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.clearFocus() }
                ),
                value = nameInput,
                onValueChanged = { nameInput = it },
            )
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "Дата Рождения",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.secondary
            )
            DateField(birthDateInput)
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "Пол",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.secondary
            )
            Row(Modifier.fillMaxWidth(1f)) {

                Button(
                    onClick = { gender = 0 },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (gender == 0) MaterialTheme.colors.primary
                        else MaterialTheme.colors.background
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                    shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
                )
                {
                    Text(
                        stringResource(R.string.male),
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.body1
                    )
                }
                Button(
                    onClick = { gender = 1 },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (gender == 1) MaterialTheme.colors.primary
                        else MaterialTheme.colors.background
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                    shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                )
                {
                    Text(
                        stringResource(R.string.female),
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
            Spacer(Modifier.height(40.dp))
            Button(
                onClick = {
                    if (errorList.isNotEmpty())
                        openDialog.value = true
                    else if (isChanged) {

                        val api = Network.getUserApi()
                        val callTargetResponse: Call<Unit> =
                            api.putProfile(
                                "Bearer ${Network.token}", ProfileResponseModel(
                                    email = emailInput,
                                    id = Network.userId!!,
                                    nickName = Network.userNickname!!,
                                    birthDate = birthDateInput.value,
                                    name = nameInput,
                                    avatarLink = avatarRefInput,
                                    gender = gender
                                )
                            )


                        callTargetResponse.enqueue(object : Callback<Unit> {

                            override fun onResponse(
                                call: Call<Unit>,
                                response: Response<Unit>
                            ) {
                                if (response.isSuccessful) {

                                    isChanged = false

                                } else errorCode.value = response.message()

                            }

                            override fun onFailure(call: Call<Unit>, t: Throwable) {
                                errorCode.value = t.localizedMessage
                            }

                        })

                    }

                }, modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(40.dp),
                colors = if (errorList.isEmpty() && isChanged) ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                ) else
                    ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.background
                    ),
                border = if (errorList.isEmpty() && isChanged) null
                else BorderStroke(1.dp, MaterialTheme.colors.secondary),
                shape = RoundedCornerShape(4.dp)
            )
            {
                Text(
                    "Сохранить",
                    color = if (errorList.isEmpty() && isChanged) Color.White
                    else MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.body2
                )

            }

            Button(
                onClick = {
                    val callTargetResponse = Network.getAuthApi().logout("Bearer ${Network.token}")

                    callTargetResponse.enqueue(object : Callback<Unit> {
                        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                            if(response.isSuccessful){
                                navController.navigate("signIn_screen")
                            }
                        }

                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                    })

                }, modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background

                ),
                shape = RoundedCornerShape(4.dp),
                elevation = ButtonDefaults.elevation(0.dp)
            )
            {
                Text(
                    "Выйти из аккаунта",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.body2
                )
            }
        }
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = { Text(text = "Ошибки", color = Black200) },
                text = {
                    Column {
                        for (i in errorList) Text(i, color = Black200)
                    }
                },
                buttons = {
                    Button(
                        onClick = { openDialog.value = false }
                    ) {
                        Text("OK", style = MaterialTheme.typography.h1)
                    }
                }
            )
        }


    }


}


@Composable
@Preview
fun Prev() {

    MovieCatalogTheme() {
        ProfileScreen(navController = rememberNavController())
    }


}