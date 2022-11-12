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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moviecatalog.DateField
import com.example.moviecatalog.EditField
import com.example.moviecatalog.NavigationBottomBar
import com.example.moviecatalog.R
import com.example.moviecatalog.ui.theme.Black200
import com.example.moviecatalog.ui.theme.MovieCatalogTheme
import com.example.moviecatalog.ui.theme.White200

@Composable
fun ProfileScreen(navController: NavController){

    Scaffold(bottomBar = { NavigationBottomBar(navController = navController)}) {
        val focusManager = LocalFocusManager.current

        var avatarRefInput by remember { mutableStateOf("") }
        var emailInput by remember { mutableStateOf("") }
        var nameInput by remember { mutableStateOf("") }
        val birthDateInput = remember { mutableStateOf("") }
        var gender by remember { mutableStateOf(-1) }
        val openDialog = remember { mutableStateOf(false) }
        val errorList = mutableListOf<String>()


        var error = emailErrors(emailInput)
        if(error!=null) {
            errorList.add(error)
        }

        error = nameErrors(nameInput)
        if(error!=null) {
            errorList.add(error)
        }

        error = birthDateErrors(birthDateInput.value)
        if(error!=null) {
            errorList.add(error)
        }

        error = genderErrors(gender)
        if(error!=null) {
            errorList.add(error)
        }






        Column(modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize(1f)
            .padding(20.dp),
            horizontalAlignment = Alignment.Start

        )
        {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start) {

                Image(
                    modifier = Modifier
                        .size(75.dp)
                    ,
                    //contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.profile_image),
                    contentDescription = "profile_image"
                )
                Spacer(Modifier.width(20.dp))
                Text(
                    text = "Тест",
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
                    if(errorList.isNotEmpty())
                        openDialog.value = true

                }, modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(40.dp),
                colors = if (errorList.isEmpty()) ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                ) else
                    ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.background
                    ),
                border =  if(errorList.isEmpty()) null
                else BorderStroke(1.dp, MaterialTheme.colors.secondary),
                shape = RoundedCornerShape(4.dp)
            )
            {
                Text(
                    "Сохранить",
                    color = if(errorList.isEmpty()) Color.White
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
                text = { Column() {
                    for(i in errorList) Text(i,color = Black200)
                } },
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
fun Prev(){

    MovieCatalogTheme() {
        ProfileScreen(navController = rememberNavController())
    }


}