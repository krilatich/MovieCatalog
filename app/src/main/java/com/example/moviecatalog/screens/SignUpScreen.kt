package com.example.moviecatalog.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviecatalog.DateField
import com.example.moviecatalog.EditField
import com.example.moviecatalog.PasswordEditField
import com.example.moviecatalog.R
import com.example.moviecatalog.data.Gender


@Composable
fun SignUpScreen(navController: NavController){

    val focusManager = LocalFocusManager.current

    var loginInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var passwordConfirmInput by remember { mutableStateOf("") }
    var nameInput by remember { mutableStateOf("") }
    val birthDateInput = remember { mutableStateOf("") }





    Column(modifier = Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxSize(1f)
        .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp))


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

        EditField( label = R.string.login,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            value = loginInput,
            onValueChanged = { loginInput = it },
        )

        EditField( label = R.string.e_mail,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            value = emailInput,
            onValueChanged = { emailInput = it },
        )

        EditField( label = R.string.name,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            value = nameInput,
            onValueChanged = { nameInput = it },
        )

        PasswordEditField( label = R.string.password,
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

        PasswordEditField( label = R.string.password_confirm,
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

        DateField(birthDateInput,R.string.birth_date)

        Spacer(Modifier.height(4.dp))

        Row(Modifier.fillMaxWidth(1f)){
            var gender by remember { mutableStateOf(Gender.NONE) }
            Button(onClick = {gender = Gender.MALE},
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (gender == Gender.MALE)  MaterialTheme.colors.primary
                    else MaterialTheme.colors.background
                ),
                border =  BorderStroke(1.dp, MaterialTheme.colors.secondary),
                shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
            )
            {
                Text(
                    stringResource(R.string.male),
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.body1
                )
            }
            Button(onClick = {gender = Gender.FEMALE},
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (gender == Gender.FEMALE)  MaterialTheme.colors.primary
                    else MaterialTheme.colors.background
                ),
                border =  BorderStroke(1.dp, MaterialTheme.colors.secondary),
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


        Button(onClick = {},modifier = Modifier
            .fillMaxWidth(1f)
            .height(40.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background
            ),
            border =  BorderStroke(1.dp, MaterialTheme.colors.secondary),
            shape = RoundedCornerShape(4.dp)
        )
        {
            Text(
                stringResource(R.string.register),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body2
            )

        }

        Button(onClick = {
                         navController.navigate("signIn_screen")
        },modifier = Modifier
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




    }

}

