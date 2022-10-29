package com.example.moviecatalog

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


import androidx.navigation.compose.rememberNavController
import com.example.moviecatalog.screens.*


@Composable
fun Navigation(){

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LaunchScreen.route){
        composable(Screen.LaunchScreen.route){
            SplashScreen(navController = navController)
        }
        composable(Screen.SignInScreen.route){
            SignInScreen(navController = navController)
        }
        composable(Screen.SignUpScreen.route){
            SignUpScreen(navController = navController)
        }
        composable(Screen.MainScreen.route){
            MainScreen(navController = navController)
        }
        composable(Screen.ProfileScreen.route){
            ProfileScreen(navController = navController)
        }

    }



}