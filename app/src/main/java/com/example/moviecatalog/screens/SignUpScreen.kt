package com.example.moviecatalog.screens

import android.annotation.SuppressLint
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moviecatalog.DateField
import com.example.moviecatalog.EditField
import com.example.moviecatalog.PasswordEditField
import com.example.moviecatalog.R
import com.example.moviecatalog.network.Network
import com.example.moviecatalog.network.RegisterRequestBody
import com.example.moviecatalog.network.TokenResponse
import com.example.moviecatalog.ui.theme.Black200
import com.example.moviecatalog.ui.theme.MovieCatalogTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("SuspiciousIndentation")
@Composable
fun SignUpScreen(navController: NavController) {


    val mContext = LocalContext.current

    val focusManager = LocalFocusManager.current

    var loginInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var passwordConfirmInput by remember { mutableStateOf("") }
    var nameInput by remember { mutableStateOf("") }
    val birthDateInput = remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(-1) }
    val openDialog = remember { mutableStateOf(false) }


    val errorList = mutableListOf<String>()

    var error: String? = loginErrors(loginInput)
    if (error != null) {
        errorList.add(error)
    }
    error = emailErrors(emailInput)
    if (error != null) {
        errorList.add(error)
    }
    error = nameErrors(loginInput)
    if (error != null) {
        errorList.add(error)
    }
    error = passwordErrors(passwordInput)
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
    if (passwordInput != passwordConfirmInput) errorList.add("Пароли не совпадают")




    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize(1f)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    )


    {

        Image(
            modifier = Modifier
                .height(50.dp)
                .width(110.dp),
            //contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.mc_logo),
            contentDescription = stringResource(R.string.mc_logo)
        )

        Text(
            text = stringResource(R.string.MovieCatalog),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.primary
        )

        Text(
            text = stringResource(R.string.Registration),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .align(Alignment.Start)
        )

        EditField(
            label = R.string.login,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            value = loginInput,
            onValueChanged = { loginInput = it },
        )

        EditField(
            label = R.string.e_mail,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            value = emailInput,
            onValueChanged = { emailInput = it },
        )

        EditField(
            label = R.string.name,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            value = nameInput,
            onValueChanged = { nameInput = it },
        )

        PasswordEditField(
            label = R.string.password,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            value = passwordInput,
            onValueChanged = { passwordInput = it },
        )

        PasswordEditField(
            label = R.string.password_confirm,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            value = passwordConfirmInput,
            onValueChanged = { passwordConfirmInput = it },
        )

        DateField(birthDateInput, R.string.birth_date)

        Spacer(Modifier.height(4.dp))



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
        Spacer(Modifier.height(20.dp))



        Button(
            onClick = {

                if (errorList.isNotEmpty())
                    openDialog.value = true
                else {
                    val authApi = Network.getAuthApi()

                    Network.userNickname = loginInput

                    val callTargetResponse = authApi.register(RegisterRequestBody(
                        userName = loginInput,
                        name = nameInput,
                        birthDate = "2022-12-26T08:06:55.508Z",
                        email = emailInput,
                        gender = gender,
                        password = passwordInput
                    ))



                    callTargetResponse.enqueue(object : Callback<TokenResponse> {
                        override fun onResponse(
                            call: Call<TokenResponse>,
                            response: Response<TokenResponse>
                        ) {
                            if(response.isSuccessful) {
                                Network.token = response.body()!!.token
                                Network.userNickname = loginInput
                                navController.navigate("main_screen")
                            }
                        }

                        override fun onFailure(call: Call<TokenResponse>, t: Throwable) {

                        }

                    }
                    )


/*
                    val result = AuthRepository().register(
                        RegisterRequestBody(
                            userName = loginInput,
                            name = nameInput,
                            birthDate = "2022-12-26T08:06:55.508Z",
                            email = emailInput,
                            gender = gender,
                            password = passwordInput
                        )
                    )
                    nameInput = Network.token.toString()
                    mToast(mContext, result)


 */
                    //navController.navigate("main_screen")

                }

            }, modifier = Modifier
                .fillMaxWidth(1f)
                .height(40.dp),
            colors = if (errorList.isEmpty()) ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary
            ) else
                ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background
                ),
            border = if (errorList.isEmpty()) null else BorderStroke(
                1.dp,
                MaterialTheme.colors.secondary
            ),
            shape = RoundedCornerShape(4.dp)
        )
        {
            Text(
                stringResource(R.string.register),
                color = if (errorList.isEmpty()) Color.White
                else MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body2
            )

        }

        Button(
            onClick = {
                navController.navigate("signIn_screen")
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
                stringResource(R.string.have_account),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body2
            )
        }

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = { Text(text = "Ошибки", color = Black200) },
                text = {
                    Column() {
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

fun loginErrors(value: String): String? {

    if (value == "") return "Пустой логин"
    return null

}

fun passwordErrors(value: String): String? {

    if (value == "") return "Пустой пароль"
    return null

}

fun emailErrors(value: String): String? {

    if (value == "") return "Пустой email"
    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches())
        return "Не правильный email"
    return null

}

fun nameErrors(value: String): String? {

    if (value == "") return "Пустое имя"
    return null

}

fun birthDateErrors(value: String): String? {

    if (value == "") return "Пустая дата рождения"
    return null

}

fun genderErrors(value: Int): String? {

    if (value == -1) return "Не выбран пол"
    return null

}


@Composable
@Preview
fun Previ() {
    MovieCatalogTheme() {
        SignUpScreen(navController = rememberNavController());
    }

}
