package com.example.moviecatalog

import android.app.DatePickerDialog

import android.os.Bundle

import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection


import androidx.compose.ui.graphics.vector.ImageVector


import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.moviecatalog.ui.theme.MovieCatalogTheme



import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieCatalogTheme {
                // A surface container using the 'background' color from the theme

                SignUpScreen()
            }
        }
    }
}




@Composable
fun SignUpScreen(){

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

        DateField(birthDateInput)

        Spacer(Modifier.height(4.dp))

        Row(Modifier.fillMaxWidth(1f)){
            var gender by remember { mutableStateOf(Gender.NONE)}
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
                Text(stringResource(R.string.male),
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
                Text(stringResource(R.string.female),
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
            shape = RoundedCornerShape(4.dp))
        {
            Text(stringResource(R.string.register),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body2
            )

        }

        Button(onClick = {},modifier = Modifier
            .fillMaxWidth(1f)
            .height(40.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background

            ),
            shape = RoundedCornerShape(4.dp),
            elevation = ButtonDefaults.elevation(0.dp)
        )
        {
            Text(stringResource(R.string.have_account),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body2
            )
        }




    }

        }










@Composable
fun EditField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        value = value,
        singleLine = true,
        modifier = modifier.fillMaxWidth().height(56.dp),
        onValueChange = onValueChanged,
        label = { Text(stringResource(label),
            color = MaterialTheme.colors.secondary,
            style = MaterialTheme.typography.body1
        )
                },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        shape = MaterialTheme.shapes.small,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = MaterialTheme.colors.secondary
            )


    )
}

@Composable
fun PasswordEditField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        singleLine = true,
        modifier = modifier.fillMaxWidth().height(56.dp),
        onValueChange = onValueChanged,
        label = { Text(stringResource(label),
            color = MaterialTheme.colors.secondary,
            style = MaterialTheme.typography.body1
        )
                },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        shape = MaterialTheme.shapes.small,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = MaterialTheme.colors.secondary
            ),
        visualTransformation = if (passwordVisible) VisualTransformation.None
        else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            // Please provide localized description for accessibility services
            val description = if (passwordVisible) "Hide password" else "Show password"
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, description)
            }
        }
    )
}

@Composable
fun ReadonlyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @StringRes label: Int,
    image: ImageVector? = null
) {
    Box {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp),
            label = { Text(stringResource(label),
                color = MaterialTheme.colors.secondary ,
                style = MaterialTheme.typography.body1
            ) },
            trailingIcon = {
                if(image!=null) 
                    Icon(imageVector = image, contentDescription = null)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                textColor = MaterialTheme.colors.primary,
            ),
            )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable(onClick = onClick),
        )
    }
}

@Composable
fun DateField(
    mDate : MutableState<String>,
    )
{
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()

    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)
    val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()


    val mDatePickerDialog = DatePickerDialog(
        mContext,R.style.DialogTheme,
        { _: DatePicker, mYearOfLife: Int, mMonthOfYear: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth.${mMonthOfYear + 1}.$mYearOfLife"
        }, mYear, mMonth, mDay
    )


    ReadonlyTextField(
        value = mDate.value,
        onValueChange = {mDate.value = it},
        onClick = { mDatePickerDialog.show() },
        label = R.string.birth_date,
        image = Icons.Default.DateRange
    )


}




@Preview
@Composable
fun DefaultPreview() {

    MovieCatalogTheme {
        SignUpScreen()
    }
}