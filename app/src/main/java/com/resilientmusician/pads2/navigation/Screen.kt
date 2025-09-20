package com.resilientmusician.pads2.navigation

sealed class Screen(val route: String) {
    object Pads : Screen("pads")
    object Editor : Screen("editor")
    object Settings : Screen("settings")
}