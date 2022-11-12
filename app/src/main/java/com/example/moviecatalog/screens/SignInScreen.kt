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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moviecatalog.EditField
import com.example.moviecatalog.PasswordEditField
import com.example.moviecatalog.R
import com.example.moviecatalog.ui.theme.Black200
import com.example.moviecatalog.ui.theme.MovieCatalogTheme

@Composable
fun SignInScreen(navController: NavController){

    val focusManager = LocalFocusManager.current

    var loginInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }

    val openDialog = remember { mutableStateOf(false) }



    val errorList = mutableListOf<String>()

    var error:String? = loginErrors(loginInput)
    if(error!=null) {
        errorList.add(error)
    }
    error = passwordErrors(passwordInput)
    if(error!=null) {
        errorList.add(error)
    }






    Column(modifier = Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxSize(1f)
        .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp))


    {

        Spacer(Modifier.height(20.dp))

        Image(
            modifier = Modifier
                .height(100.dp)
                .width(200.dp),
            painter = painterResource(id = com.example.moviecatalog.R.drawable.mc_logo),
            contentDescription = stringResource(com.example.moviecatalog.R.string.mc_logo)
        )

        Text(
            text = stringResource(com.example.moviecatalog.R.string.MovieCatalog),
            style = TextStyle(fontSize = 35.sp, fontFamily =
            FontFamily(Font(com.example.moviecatalog.R.font.ibmplex_bold))
            ),
            color = MaterialTheme.colors.primary
        )

        Spacer(Modifier.height(50.dp))

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


        PasswordEditField( label = R.string.password,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            value = passwordInput,
            onValueChanged = { passwordInput = it },
        )

        Spacer(Modifier.height(200.dp))


        Button(onClick = {
            if(errorList.isNotEmpty())
                openDialog.value = true
            else
                navController.navigate("main_screen")
        },modifier = Modifier
            .fillMaxWidth(1f)
            .height(40.dp),
            colors = if (errorList.isEmpty()) ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary
            ) else
                ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background
                ),
            border =  if(errorList.isEmpty()) null else BorderStroke(1.dp, MaterialTheme.colors.secondary),
            shape = RoundedCornerShape(4.dp)
        )
        {
            Text(
                text = stringResource(R.string.LogIn),
                color = if(errorList.isEmpty()) Color.White
                else MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body2
            )

        }

        Button(onClick = {
                         navController.navigate("signUp_screen")
        },modifier = Modifier
            .fillMaxWidth(1f)
            .height(40.dp),
            colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background
                ),
            border =  null,
            shape = RoundedCornerShape(4.dp),
            elevation = ButtonDefaults.elevation(0.dp)
        )
        {
            Text(
                text = stringResource(R.string.SignUp),
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

@Composable
@Preview
fun Preview1(){
    MovieCatalogTheme() {
        SignInScreen(navController = rememberNavController())
    }

}