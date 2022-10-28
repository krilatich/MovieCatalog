package com.example.moviecatalog


import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.ui.theme.MovieCatalogTheme
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

