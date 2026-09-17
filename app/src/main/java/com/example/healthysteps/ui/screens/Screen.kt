package com.example.healthysteps.ui.screens

sealed class Screen(val route: String) {

    data object Welcome : Screen("welcome")

    data object Login : Screen("login")

    data object Register : Screen("register")

    data object Dashboard : Screen("dashboard")

    data object Habits : Screen("habits")

    data object AddHabit : Screen("add_habit")

    data object Progress : Screen("progress")

    data object Achievements : Screen("achievements")

    data object Profile : Screen("profile")

    data object Settings : Screen("settings")

    data object Language : Screen("language")

    data object FoodScanner : Screen("food_scanner")

    data object FoodDetails : Screen("food_details")
}