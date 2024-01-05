package com.ppk.currencyxchanger.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ppk.currencyxchanger.presentation.view.Home

@Composable
internal fun NavGraph() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Home.route) {
        composable(Routes.Home.route) {
            Home(navController = navController)
        }

    }

}

sealed class Routes(val route: String) {
    object Home : Routes("barcodeScanner")
}
