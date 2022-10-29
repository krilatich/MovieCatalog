package com.example.moviecatalog


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviecatalog.ui.theme.MovieCatalogTheme
import com.example.moviecatalog.ui.theme.Orange200
import com.example.moviecatalog.ui.theme.White200
import com.example.moviecatalog.ui.theme.barColor
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieCatalogTheme {
                // A surface container using the 'background' color from the theme

                Navigation()
            }
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
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
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
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
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

@SuppressLint("ResourceType")
@Composable
fun NavigationBottomBar(navController: NavController,modifier: Modifier = Modifier){
    Row(
        Modifier
            .background(color = barColor)
            .height(50.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically){

            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable(onClick = {navController.navigate("main_screen")})) {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "HomeIcon",
                    tint = if(navController.currentDestination?.route == Screen.MainScreen.route)
                        MaterialTheme.colors.primary
                    else MaterialTheme.colors.secondary
            )
                Text(
                    text = "Главное",
                    style = MaterialTheme.typography.body1,
                    color = if(navController.currentDestination?.route == Screen.MainScreen.route)
                        MaterialTheme.colors.primary
                                else MaterialTheme.colors.secondary,
                    )

            }

        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable(onClick = {navController.navigate("profile_screen")})) {
            Icon(
                Icons.Filled.Person,
                contentDescription = "PersonIcon",
                tint = if(navController.currentDestination?.route == Screen.ProfileScreen.route)
                    MaterialTheme.colors.primary
                else MaterialTheme.colors.secondary
            )
            Text(
                text = "Профиль",
                style = MaterialTheme.typography.body1,
                color = if(navController.currentDestination?.route == Screen.ProfileScreen.route)
                    MaterialTheme.colors.primary
                else MaterialTheme.colors.secondary,
            )

        }


    }
}


@Composable
fun RatingIcon(rating: Double){
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = if (rating<4) Color.Red
        else if (rating<6) Orange200
        else if(rating<8) Color.Yellow
        else Color.Green
    ) {
        Text(
            text = rating.toString(),
            style = MaterialTheme.typography.body2,
            color = White200,
            modifier = Modifier.padding(start = 5.dp,end = 5.dp)
        )
    }

}


@Preview
@Composable
fun Pr(){
    MovieCatalogTheme {
       RatingIcon(10.0)
    }
}