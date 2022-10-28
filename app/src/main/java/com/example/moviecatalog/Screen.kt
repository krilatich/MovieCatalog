package com.example.moviecatalog

sealed class Screen(val route:String){
    object LaunchScreen:Screen("launch_screen")
    object SignInScreen:Screen("signIn_screen")
    object SignUpScreen:Screen("signUp_screen")

}
