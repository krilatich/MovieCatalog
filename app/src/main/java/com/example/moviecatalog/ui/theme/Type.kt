package com.example.moviecatalog.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.moviecatalog.R


// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.ibmplex_regular) ,
        ),
        fontSize = 12.sp
    ),
    h1 = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.ibmplex_bold) ,
        ),
        fontSize = 20.sp
    ),
    h2 = TextStyle(

        fontFamily = FontFamily(
            Font(R.font.ibmplex_bold) ,
        ),

        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.ibmplex_medium) ,
        ),
        fontSize = 14.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)